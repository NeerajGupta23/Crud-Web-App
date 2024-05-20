const iFrame = document.getElementsByTagName("iframe").item(0);

const createPointer = document.getElementById("Create");
const readPointer = document.getElementById("Read");
const updatePointer = document.getElementById("Update");
const deletePointer = document.getElementById("Delete");
let prevOperation;

function changeIFrame(location) {
  iFrame.setAttribute("src", "./html/" + location);
}
function setStyle(setPointer) {
  setPointer.setAttribute("style", "background-color: #2a9798;");

  if (prevOperation && prevOperation !== setPointer) {
    prevOperation.removeAttribute("style");
  }
  prevOperation = setPointer;
}

createPointer.addEventListener("click", () => {
  changeIFrame("create.html");
  setStyle(createPointer);
});

readPointer.addEventListener("click", () => {
  changeIFrame("read.html");
  setStyle(readPointer);
});

updatePointer.addEventListener("click", () => {
  changeIFrame("update.html");
  setStyle(updatePointer);
});

deletePointer.addEventListener("click", () => {
  changeIFrame("delete.html");
  setStyle(deletePointer);
});
