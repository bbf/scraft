'use strict';

const os = require('os');

//const {VM} = require('vm2');

function dummy() {
	return "Hello, Uptime: " + os.uptime();
}



var http = require('http');

var server = http.createServer(function (request, response) {
	response.writeHead(200, {'Content-Type': 'text/plain'});
	response.end(dummy());
});

server.listen(8000);

console.log('Server running at http://127.0.0.1:8000/')
