import http from 'k6/http';

// Default option
export const options = {
    vus: 10,
    duration: '10s',
};

function test_case_1() {
    http.get("http://sb-perf:8080");
}


function test_case_2() {
    const url = __ENV.URL;
    const test = __ENV.TEST || Math.floor(Math.random() * 1000);
    const payload = JSON.stringify({
        "objectId":"test-" + test,"eserviceId":"test-postgres","objectType":"CREATE","signalType":"CREATE"
    });
    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };
    http.post(url, payload, params);
}

export default function () {
    // test_case_1();
    test_case_2();
}
