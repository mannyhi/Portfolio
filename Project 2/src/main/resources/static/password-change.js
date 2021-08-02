async function invalid() {
    let url = 'http://localhost:9001/invalidpassword'
    let response = await fetch(url)
    let element = document.getElementById("invalid")
    let msg = await response.json();
    if (msg == "invalid"){
        element.innerHTML += "Old password incorrect"
        document.getElementById("oldpassword").style.border = "thick solid #F26925";
    }
    else if (msg == "unmatched"){
        element.innerHTML += "Passwords do not match"
        document.getElementById("newpassword").style.border = "thick solid #F26925";
        document.getElementById("retype").style.border = "thick solid #F26925";
    }
}

window.onload = function () {
    this.invalid()
}