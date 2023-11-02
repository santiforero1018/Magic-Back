var connect = (function () {

    var lastPt = null;
    var canvas;
    var ctx;
    let code;
    let drawingPoint = [];
    var assignedCanvasId;


    class Point {
        constructor(x, y) {
            this.x = x;
            this.y = y;
        }
    }

    var stompClient = null;

    var randomKey = function () {
        var random = Math.random();
        code = Math.ceil(random * 900000).toString().padStart(6, '0');
        window.location.href = 'board.html?code=' + code;
    }


    var draw = function (event) {
        var relative = canvas.getBoundingClientRect();
        var relX = event.clientX - relative.left, relY = event.clientY - relative.top;

        if (!lastPt) {
            lastPt = { x: relX, y: relY };
        }

        ctx.beginPath();
        ctx.moveTo(lastPt.x, lastPt.y);
        ctx.lineTo(relX, relY);
        ctx.stroke();
        lastPt = { x: relX, y: relY };
        drawingPoint.push(new Point(relX, relY));
    }

    var endPointer = function (event) {
        canvas.removeEventListener("pointermove", draw, false);
        canvas.removeEventListener("mousemove", draw, false);

        //Set last point to null to end our pointer path
        lastPt = null;
    }

    var connectAndSubscribe = function () {
        console.info('Connecting to WS...');
        var socket = new SockJS("/stompendpoint");
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe("/game/room." + code, function (eventbody) {
                var canvasData = JSON.parse(eventbody.body);
                handleDrawEvent(canvasData);

            });
        });

    };

    var connectToRoom = function () {
        code = $('#joinMe').val();
        window.location.href = 'board.html?code=' + code;
    }

    var handleDrawEvent = function (canvasData) {
        if (Array.isArray(canvasData.drawingData) && canvasData.drawingData.length > 0) {
            var canvasDraw = document.getElementById(canvasData.canvasId);
            var ctxN = canvasDraw.getContext("2d");
            ctxN.beginPath();
            for (var i = 0; i < canvasData.drawingData.length; i++) {
                ctxN.lineTo(canvasData.drawingData[i].x, canvasData.drawingData[i].y);
            }
            ctxN.stroke();
        }

    }

    var sendCanvasData = function () {
        var canvasData = {
            canvasId: assignedCanvasId,
            drawingData: drawingPoint
        };
        stompClient.send("/app/room." + code, {}, JSON.stringify(canvasData));
        drawingPoint = [];
    }

    var requestCanvasAssignment = function (roomCode) {
        return new Promise((resolve, reject) => {
            // Realiza una solicitud al servidor para obtener la asignación del canvas y el roomCode

            $.ajax({
                url: "/API-v1.0MagicBrushStrokes/board",
                type: 'POST',
                contentType: "application/json",
                data: JSON.stringify({ roomCode: roomCode }),
                success: function(data){
                    assignedCanvasId = data.canvasId;
                    roomCode = data.roomCode;
                    resolve();
                },
                error: function(error){
                    reject(error);
                }

            });

        });
    };

    return {

        init: function () {
            var urlParams = new URLSearchParams(window.location.search);
            code = urlParams.get('code');
            console.log("Room code: " + code);
            // Obtén el ID de canvas asignado al usuario desde la cookie o el almacenamiento local
            assignedCanvasId = localStorage.getItem('assignedCanvasId');
            if (!assignedCanvasId) {
                requestCanvasAssignment(code)
                    .then(() => {
                        if (assignedCanvasId === "FULLROOM") {
                            console.log("The room is full, no canvas Assigned");
                            alert("The room is full, no canvas Assigned");
                        } else {
                            canvas = document.getElementById(assignedCanvasId);
                            console.log("Canvas: " + assignedCanvasId);
                            ctx = canvas.getContext("2d");
                            canvas.addEventListener("pointerdown", function () {
                                canvas.addEventListener("pointermove", draw, false);

                                drawingPoint = [];
                            }, false);
                            canvas.addEventListener("pointerup", function () {
                                endPointer();
                                sendCanvasData();
                            });
                            connectAndSubscribe();
                        }
                    });
            } else {
                canvas = document.getElementById(assignedCanvasId);
                console.log("Canvas: " + assignedCanvasId);
                ctx = canvas.getContext("2d");
                canvas.addEventListener("pointerdown", function () {
                    canvas.addEventListener("pointermove", draw, false);

                    drawingPoint = [];
                }, false);
                canvas.addEventListener("pointerup", function () {
                    endPointer();
                    sendCanvasData();
                });
                connectAndSubscribe();
            }
        },

        connectAndSubscribe,
        randomKey,
        connectToRoom,
    }

})();