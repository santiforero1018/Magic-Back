var connect = (function () {

    var lastPt = null;
    var canvas;
    var ctx;

    class Point {
        constructor(x, y) {
            this.x = x;
            this.y = y;
        }
    }

    var stompClient = null;

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
    }

    var endPointer = function(event){
        canvas.removeEventListener("pointermove", draw, false); 
        canvas.removeEventListener("mousemove", draw, false); 
 
          //Set last point to null to end our pointer path
        lastPt = null;
    }

    var connectAndSubscribe = function (topic) {
        console.info('Connecting to WS...');
        var socket = new SockJS("/stompendpoint");
        stompClient = Stomp.over(socket);
        
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/game' + topic, function (eventbody) {
                var pt = JSON.parse(eventbody.body);               
            });
        });

    };


    return {

        init: function () {
            canvas = document.getElementById("canvas1");
            ctx = canvas.getContext("2d");
            if (window.PointerEvent) {
                canvas.addEventListener("pointerdown", function () {
                    canvas.addEventListener("pointermove", draw, false);
                    stompClient.send("/app/newpoint", {}, JSON.stringify(pt))
                }, false);
                canvas.addEventListener("pointerup", endPointer, false);
            }
            connectAndSubscribe();
        }
    }

})();