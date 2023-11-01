var connect = (function () {

    var lastPt = null;
    var canvas;
    var ctx;
    let code;
    let drawingPoint = [];
    

    class Point {
        constructor(x, y) {
            this.x = x;
            this.y = y;
        }
    }

    var stompClient = null;

    var randomKey = function(){
        var random = Math.random();
        code = Math.ceil(random * 900000).toString().padStart(6,'0');
        window.location.href = 'board.html?code=' + code;
    }


    var draw = function (event){
        if(lastPt!=null) {
            ctx.beginPath();
            // Start at previous point
            ctx.moveTo(lastPt.x, lastPt.y);
            // Line to latest point
            ctx.lineTo(event.pageX, event.pageY);
            // Draw it!
            ctx.stroke();
          }
          //Store latest pointer
          lastPt = {x:event.pageX, y:event.pageY};
          drawingPoint.push(new Point(event.pageX, event.pageY));

    }

    var endPointer = function(event){
        canvas.removeEventListener("pointermove", draw, false); 
        canvas.removeEventListener("mousemove", draw, false); 
 
          //Set last point to null to end our pointer path
        lastPt = null;
    }

    var connectAndSubscribe = function () {
        console.info('Connecting to WS...');
        var socket = new SockJS("/stompendpoint");
        stompClient = Stomp.over(socket);
        
        //var room = $('#room').val();

        // console.log(model.getCode());

        // randomKey();

        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe("/game/room."+code, function (eventbody) {
                var pts = JSON.parse(eventbody.body);
                handleDrawEvent(pts);        
            });
        });

    };

    var connectToRoom = function (){
        code = $('#joinMe').val();
        window.location.href = 'board.html?code=' + code;
    }

    var handleDrawEvent = function (points) {
        ctx.beginPath();
        ctx.moveTo(points[0].x, points[0].y);
        for (var i = 1; i < points.length; i++) {
            ctx.lineTo(points[i].x, points[i].y);
        }
        ctx.stroke();
    }


    return {

        init: function () {
            var urlParams = new URLSearchParams(window.location.search);
            code = urlParams.get('code');
            canvas = document.getElementById("canvas1");
            ctx = canvas.getContext("2d");
            if (window.PointerEvent) {
                canvas.addEventListener("pointerdown", function () {
                    canvas.addEventListener("pointermove", draw, false);
                    stompClient.send("/app/room."+code, {}, JSON.stringify(drawingPoint));
                    drawingPoint = [];
                    // endPointer();
                }, false);
                canvas.addEventListener("pointerup", endPointer, false);
            }
            connectAndSubscribe();
        },

        connectAndSubscribe,
        randomKey,
        connectToRoom,
    }

})();