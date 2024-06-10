        function getLocation() {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(showPosition);
            } else {
                alert("Geolocation is not supported by this browser.");
            }
        }

        function showPosition(position) {
            document.getElementById('lat').value = position.coords.latitude;
            document.getElementById('lnt').value = position.coords.longitude;
        }
