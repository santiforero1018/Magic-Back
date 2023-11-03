var connect = (function () {

    var lastPt = null;
    var canvas;
    var ctx;
    let code;
    let drawingPoint = [];
    var assignedCanvasId;
    var wrongCanvas1;
    var wrongCanvas2;
    var wrongCanvas3;


    class Point {
        constructor(x, y) {
            this.x = x;
            this.y = y;
        }
    }

    var stompClient = null;

    var randomKey = function () {
        var room = document.getElementById("room");
        if (room.value === "") {
            var random = Math.random();
            code = Math.ceil(random * 900000).toString().padStart(6, '0');
        } else {
            code = room.value;
        }
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

        else {
            var allCanvas = ["canvas1", "canvas2", "canvas3", "canvas4"];
            var index = allCanvas.indexOf(canvasData.canvasId);
            allCanvas.splice(index, 1);
            var erase1 = document.getElementById(allCanvas[0]);
            var erase2 = document.getElementById(allCanvas[1]);
            var erase3 = document.getElementById(allCanvas[2]);
            var cer1 = erase1.getContext("2d");
            var cer2 = erase2.getContext("2d");
            var cer3 = erase3.getContext("2d");
            cer1.clearRect(0, 0, canvas.width, canvas.height);
            cer2.clearRect(0, 0, canvas.width, canvas.height);
            cer3.clearRect(0, 0, canvas.width, canvas.height);
        }

    }

    var sendCanvasData = function () {
        var canvasData = {
            canvasId: assignedCanvasId,
            drawingData: drawingPoint,
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
                success: function (data) {
                    assignedCanvasId = data.canvasId;
                    roomCode = data.roomCode;
                    resolve();
                },
                error: function (error) {
                    reject(error);
                }

            });

        });
    };

    var prepareUnassignedCanvas = function () {
        var allCanvas = ["canvas1", "canvas2", "canvas3", "canvas4"];
        var index = allCanvas.indexOf(assignedCanvasId);
        allCanvas.splice(index, 1);
        wrongCanvas1 = document.getElementById(allCanvas[0]);
        wrongCanvas2 = document.getElementById(allCanvas[1]);
        wrongCanvas3 = document.getElementById(allCanvas[2]);
        wrongCanvas1.addEventListener("click", handleCanvasClick);
        wrongCanvas2.addEventListener("click", handleCanvasClick);
        wrongCanvas3.addEventListener("click", handleCanvasClick);
    };

    var handleCanvasClick = function (event) {
        const clickedCanvas = event.target;
        if (clickedCanvas !== wrongCanvas1 || clickedCanvas !== wrongCanvas2 || clickedCanvas !== wrongCanvas3) {
            alert("Canvas equivocado, se te asigno: " + assignedCanvasId);
        }
    };


    let powerActive = false;

    // Función para activar el "poder"
    const activatePower = () => {

        if (!powerActive) {
            // Activa el "poder" y realiza la lógica para borrar los canvas de los demás jugadores
            powerActive = true;
            alert("¡Has activado el poder! Los demás canvas se borrarán.");

            // Lógica para borrar los canvas de los demás jugadores
            // Aquí debes enviar un mensaje a los otros jugadores para que borren sus canvas
            // Puedes usar WebSockets para esto, similar a como manejas los dibujos.
            // Agrega la siguiente lógica en tu script JavaScript existente

            // Escucha el mensaje de "borrado" y borra el canvas si se recibe
            var canvasData = {
                canvasId: assignedCanvasId,
                drawingData: drawingPoint,
                power: true
            };
            stompClient.send("/app/room." + code, {}, JSON.stringify(canvasData));

        }
    };



    return {

        init: function () {

            var urlParams = new URLSearchParams(window.location.search);
            code = urlParams.get('code');
            console.log("Room code: " + code);
            // Escucha el clic en el "poder" y llama a la función de activación
            document.getElementById("powerButton").addEventListener("click", activatePower);
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
                            prepareUnassignedCanvas();
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