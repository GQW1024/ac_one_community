window.onload = function () {
    var closeable = window.localStorage.getItem("closable");
    if (closeable){
        window.close();
        window.localStorage.removeItem("closable")
    }
}