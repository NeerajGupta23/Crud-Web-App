const iFrame = document.getElementsByTagName("iframe").item(0);

function changeIFrame(location) {
    iFrame.setAttribute("src", "./html/" + location);
}

document.getElementById("Create").addEventListener("click", () => {
    changeIFrame("create.html");
});

document.getElementById("Read").addEventListener("click", () => {
    changeIFrame("read.html");
});

document.getElementById("Update").addEventListener("click", () => {
    changeIFrame("update.html");
});

document.getElementById("Delete").addEventListener("click", () => {
    changeIFrame("delete.html");
});