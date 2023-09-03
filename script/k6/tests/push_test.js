import http from 'k6/http';
import { scenario } from 'k6/execution';
import redis from 'k6/experimental/redis';

// Default option
export const options = {
    vus: 10,
    duration: '10s',
};

const redis_addrs = __ENV.REDIS_ADDRS || '';
const redis_password = __ENV.REDIS_PASSWORD || '';
const signalhub_url = __ENV.URL;
const test = __ENV.TEST || Math.floor(Math.random() * 1000);

const object_types = ["TYPE1", "TYPE2", "TYPE3", "TYPE4"];
const eservice_ids = [
    "f47ac10b-58cc-4372-a567-0e02b2c3d3f7",
    "62e21082-4c6a-4974-867a-516f53c6d78d",
    "6512790c-f922-42b7-a159-f7b9497e7727",
    "06e271e3-7460-4051-9730-5842143548f9"
];
const signal_types = ["CREATE", "UPDATE", "DELETE"];


// Instantiate a new redis client
const redisClient = new redis.Client({
    addrs: redis_addrs.split(',') || new Array('localhost:6379'), // in the form of 'host:port', separated by commas
    password: redis_password,
});

async function test_case() {

    let randomIndex = Math.floor(Math.random() * object_types.length);
    const randomObjectType = object_types[randomIndex];

    randomIndex = Math.floor(Math.random() * eservice_ids.length);
    const randomEServiceId = eservice_ids[randomIndex];

    randomIndex = Math.floor(Math.random() * signal_types.length);
    const randomSignalType = signal_types[randomIndex];

    const signalId = await redisClient.incr(randomEServiceId);
    //console.log("signalId= " + signalId);

    const payload = JSON.stringify({
        "signalId": signalId, "objectId": "test-" + test,
        "eserviceId": randomEServiceId,
        "objectType": randomObjectType,
        "signalType": randomSignalType
    });
    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };
    http.post(signalhub_url, payload, params);
}

export default function () {
    test_case();
}
