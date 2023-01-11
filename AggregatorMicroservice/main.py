import requests
from flask import Flask, request, Response, jsonify
from flask_cors import CORS, cross_origin
from suds.client import Client

app = Flask(__name__)
CORS(app)

idm_client = Client('http://localhost:8090/ws/users.wsdl').service
DATABASE_MICROSERVICE_URL = "http://localhost:23050/"


@app.route("/api/login", methods=['POST'])
@cross_origin()
def login():
    global idm_client
    request_body = request.get_json()
    email = request_body['email']
    password = request_body['password']

    reply = idm_client.login(email, password)
    print(reply)
    token = reply['token']
    user = reply['user']
    user_id = user['id']
    user_roles = []
    for role in user['roles']:
        user_roles.append(role['id'])

    data = {'token': token, 'id': user_id, 'roles': user_roles}

    return jsonify(data)


@app.route("/<path:path>", methods=["GET", "POST"])
def proxy(path):
    global DATABASE_MICROSERVICE_URL
    if request.method == "GET":
        resp = requests.get(f"{DATABASE_MICROSERVICE_URL}{path}")
        excluded_headers = ["content-encoding", "content-length", "transfer-encoding", "connection"]
        headers = [(name, value) for (name, value) in resp.raw.headers.items() if name.lower() not in excluded_headers]
        response = Response(resp.content, resp.status_code, headers)
        return response
    elif request.method == "POST":
        resp = requests.post(f"{DATABASE_MICROSERVICE_URL}{path}", data=request.data,
                             headers={'content-type': 'application/json'})
        excluded_headers = ["content-encoding", "content-length", "transfer-encoding", "connection"]
        headers = [(name, value) for (name, value) in resp.raw.headers.items() if name.lower() not in excluded_headers]
        response = Response(resp.content, resp.status_code, headers)
        return response


if __name__ == "__main__":
    app.run(debug=True, port=5000)
