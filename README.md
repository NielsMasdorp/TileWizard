# TileWizard [Alpha! Unstable!]

![Alt text](/screenshots/screenshot1.jpg?raw=true "Settings")
![Alt text](/screenshots/screenshot2.png?raw=true "Quick Settings")

## Functionality:

- Add up to 5 Wi-Fi Energy Sockets to Android Quick Settings.

## What do you need:

- Android device with Android 7.0 or higher
- One or more Energy Sockets

1. Turn on local API in the settings for a particular socket in the Energy app.
2. Find the IP Address of the Energy Socket and give it a static IP in your router's settings

You can test the socket in the browser when, and only when, you are connected to the same Wi-Fi network as your socket.
Go to: `http://{ip}/api/v1/state` and you should see a JSON response showing the status of the socket.

```
<Request>
GET http://{IP address}/api/v1/state HTTP/1.1

<Response>
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: <length>

{
   "power_on": true,
   "switch_lock": false,
   "brightness": 255
}
```

3. Add the IP Address to the app and give the socket a name
4. Set the switch to on (Note: this does not turn on the socket, but creates a new tile in quick setting with the provided info)
5. Go to the settings of your Quick Settings panel (click the little edit icon in your notification shade) and drag the new tile to your visibile tiles
6. It might take a couple of swipes of your notification shade to initialise the tile. When it is greyed out it means the socket could not be found (for example: when you are not connected to the same network)
7. When everything has went well, the tile will indicate on or off, and you can click the tile to turn on/off the socket
8. Yay!

# Disclaimer

TileWizard is not an official product of HomeWizard, therefore there is no support and no guarantee this utility will keep working.
Use at your own risk, I am not liable for any damages related to using TileWizard.

# License

```
Copyright 2021 Niels Masdorp

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
