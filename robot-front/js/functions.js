const gridWidth = 5;
const gridHeight = 5;
const commands = ["POSITION 1 3 EAST", "FORWARD 3", "WAIT", "TURNAROUND", "FORWARD 1", "RIGHT", "FORWARD 2"];

function addInitialInputs() {
    commands.forEach((value, index) => {
        document.getElementById("inputs").innerHTML += '<div><input type="text" class="input" name="inputs[]" value="' + value + '" disabled /></div>';
    });  
}

function getCommands() {
  const inputs = document.getElementsByName('inputs[]');
  var inputValues = [];

  for(const input of inputs) {
    inputValues.push(input.value);
  }

  return inputValues;
}

async function sendCommands(commands) {

  if(commands === undefined) {
    return;
  }
  
  var myHeaders = new Headers();
  myHeaders.append("Content-Type", "application/json");

  var raw = JSON.stringify({
    "gridWidth": gridWidth,
    "gridHeight": gridHeight,
    "commands": commands
  });

  var requestOptions = {
    method: 'POST',
    headers: myHeaders,
    body: raw,
    redirect: 'follow'
  };

  await fetch("http://localhost:8080/robot/commands", requestOptions)
    .then(response => {
      try { 
        return response.json();
      } catch(jsonParseError) {
        alert(jsonParseError);
      }
    })
    .then(json => {
      console.log(json);
      if(json.status === "failure") {
        alert(json.error.message);
      } else {
        drawGrid(json.data.x, json.data.y, json.data.direction);
      }      
    })
    .catch(error => alert(error));
}

async function drawGrid(gridX, gridY, direction) {
  document.getElementById("grid").width = 44 * gridWidth;
  document.getElementById("grid").height = 44 * gridHeight;

  var robotImageSrc = "./images/south.png"
  if(direction === "NORTH") {
    robotImageSrc = "./images/north.png"
  } else if (direction === "WEST") {
    robotImageSrc = "./images/west.png"
  } else if (direction === "EAST") {
    robotImageSrc = "./images/east.png"
  }

  const robot_image = new Image();
  await new Promise(
    (r) => (robot_image.onload = r),
    (robot_image.src = robotImageSrc)
  );

  const empty_image = new Image();
  await new Promise(
    (r) => (empty_image.onload = r),
    (empty_image.src = "./images/empty.png")
  );

  var ctx = document.getElementById("grid").getContext("2d");

  for (var x = 0, i = 0; i < gridWidth; x += 44, i++) {
    for (var y = 0, j = 0; j < gridHeight; y += 44, j++) {
      if (i === gridX && j === gridY) {
        ctx.drawImage(robot_image, x, y);
      } else {
        ctx.drawImage(empty_image, x, y);
      }
    }
  }
}
