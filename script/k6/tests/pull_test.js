import http from 'k6/http';
import { URL } from 'https://jslib.k6.io/url/1.0.0/index.js';
import { Trend } from 'k6/metrics';

// Default option
export const options = {
    vus: 10,
    duration: '10s',
};

const resultsNumber = new Trend('results_number');

const signalhub_url = __ENV.URL;

const eservice_ids = [
    "f47ac10b-58cc-4372-a567-0e02b2c3d3f7",
    "62e21082-4c6a-4974-867a-516f53c6d78d",
    "6512790c-f922-42b7-a159-f7b9497e7727",
    "06e271e3-7460-4051-9730-5842143548f9"
];

async function test_case() {

    let randomIndex = Math.floor(Math.random() * eservice_ids.length);
    const randomEServiceId = eservice_ids[randomIndex];
    const number = Math.floor(Math.random() * 100);

    const url = new URL(signalhub_url);

    url.searchParams.append('eserviceId', randomEServiceId);
    url.searchParams.append('lastSignalId', number);

    const response = http.get(url.toString());
    // console.log(JSON.parse(response.body).length);
    if (response && response.body) {
        resultsNumber.add(JSON.parse(response.body).length);
    }
}

export default function () {
    test_case();
}
