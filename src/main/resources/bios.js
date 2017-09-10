'use strict';

const
os = require('os');

// const {VM} = require('vm2');

(function(g) {
	// BIOS Globals
	var _eventHandlers = {};

	// onEvent <-- called by J2V8
	g.onEvent = function() {
		var eventName = arguments[0];
		var args = [];
		var i;

		for (i = 1; i < arguments.length; i++) {
			args.push(arguments[i]);
		}

		if (eventName in _eventHandlers) {
			var handlers = _eventHandlers[eventName];
			handlers.forEach(function(handler) {
				handler.apply(null, args);
			});
		}
	};

	// Event handler registration
	g.on = function(eventName, handler) {
		var handlers;
		if (eventName in _eventHandlers) {
			handlers = _eventHandlers[eventName];
		} else {
			handlers = [];
			_eventHandlers[eventName] = handlers;
		}
		handlers.push(handler);
	};

	setInterval(function() {
		// force NodeJS.handleMessage() to pump events
	}, 100);

})(global);

on("newNexus", function() {
	sysout("New Nexus!");
});

sysout("Loaded bios..");

// function dummy() {
// return "Hello, Uptime: " + os.uptime();
// }
// var http = require('http');
// var server = http.createServer(function (request, response) {
// response.writeHead(200, {'Content-Type': 'text/plain'});
// response.end(dummy());
// });
// server.listen(8000);
// console.log('Server running at http://127.0.0.1:8000/')
