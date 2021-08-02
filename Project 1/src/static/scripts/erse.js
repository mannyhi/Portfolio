async function get_name() {
    let url = 'http://localhost:5000/name'
    let response = await fetch(url)
    let name = await response.json()
    if (name == "none"){window.location.replace('http://localhost:5000/ers.html') }
    else {
        for (let key in name) {
            let element = document.getElementById("user")
            element.innerHTML += ":  " + key
        }}
}

async function get_evp() {
    let url = 'http://localhost:5000/evp'
    let response = await fetch(url)
    let evp = await response.json()
    if (evp == "none"){window.location.replace('http://localhost:5000/ers.html') }
    else {
        if (Object.keys(evp).length === 0) {
            let table = document.getElementById("evpTable")
            var rowCount = table.rows.length
            var row = table.insertRow(1)
            row.insertCell(0).innerHTML= "none"
            row.insertCell(1).innerHTML= "-"
            row.insertCell(2).innerHTML= "-"
            row.insertCell(3).innerHTML= "-"
            row.insertCell(4).innerHTML= "-"
        }
        for (let key in evp) {
            let table = document.getElementById("evpTable")
            var rowCount = table.rows.length
            var row = table.insertRow(rowCount)
            row.insertCell(0).innerHTML= key
            row.insertCell(1).innerHTML= evp[key].ramount
            row.insertCell(2).innerHTML= evp[key].reason
            row.insertCell(3).innerHTML= evp[key].rtime
            row.insertCell(4).innerHTML= evp[key].rstatus
        }}
}

async function get_evh() {
    let url = 'http://localhost:5000/evh'
    let response = await fetch(url)
    let evh = await response.json()
    if (evh == "none"){window.location.replace('http://localhost:5000/ers.html') }
    else {
        for (let key in evh) {
            let table = document.getElementById("evhTable")
            var rowCount = table.rows.length
            var row = table.insertRow(rowCount)
            row.insertCell(0).innerHTML= key
            row.insertCell(1).innerHTML= evh[key].ramount
            row.insertCell(2).innerHTML= evh[key].reason
            row.insertCell(3).innerHTML= evh[key].rstatus
            row.insertCell(4).innerHTML= evh[key].rmessage
            row.insertCell(5).innerHTML= evh[key].rtime
            row.insertCell(6).innerHTML= evh[key].ctime
            row.insertCell(7).innerHTML= evh[key].cname
        }}
}

window.onload = function () {
    this.get_name()
    this.get_evp()
    this.get_evh()
}