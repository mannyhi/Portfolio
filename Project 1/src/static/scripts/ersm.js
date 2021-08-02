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

async function get_stats() {
    let url = 'http://localhost:5000/stats'
    let response = await fetch(url)
    let stats = await response.json()
    if (stats == "none"){window.location.replace('http://localhost:5000/ers.html') }
    else {
        let url2 = 'http://localhost:5000/bg'
        let response2 = await fetch(url2)
        let stats2 = await response2.json()
        for (let key in stats) {
            let table = document.getElementById("statsTable")
            var rowCount = table.rows.length
            var row = table.insertRow(rowCount)
            row.insertCell(0).innerHTML= stats[key].total
            row.insertCell(1).innerHTML= stats[key].approved
            row.insertCell(2).innerHTML= stats[key].denied
            row.insertCell(3).innerHTML= stats[key].pending
            row.insertCell(4).innerHTML= stats[key].mean
            row.insertCell(5).innerHTML= stats2['bg'].ename
            row.insertCell(6).innerHTML= stats2['bg'].money
        }}
}

async function get_mvp() {
    let url = 'http://localhost:5000/mvp'
    let response = await fetch(url)
    let mvp = await response.json()
    if (mvp == "none"){window.location.replace('http://localhost:5000/ers.html') }
    else {
        if (Object.keys(mvp).length === 0) {
            let table = document.getElementById("mvpTable")
            var rowCount = table.rows.length
            var row = table.insertRow(1)
            row.insertCell(0).innerHTML= "none"
            row.insertCell(1).innerHTML= "-"
            row.insertCell(2).innerHTML= "-"
            row.insertCell(3).innerHTML= "-"
            row.insertCell(4).innerHTML= "-"
            row.insertCell(5).innerHTML= "-"
            row.insertCell(6).innerHTML= "-"
            row.insertCell(7).innerHTML= "-"
            row.insertCell(8).innerHTML= ""
        }
        for (let key in mvp) {
            let table = document.getElementById("mvpTable")
            var rowCount = table.rows.length
            var row = table.insertRow(rowCount)
            row.insertCell(0).innerHTML= key
            row.insertCell(1).innerHTML= mvp[key].ename
            row.insertCell(2).innerHTML= mvp[key].eid
            row.insertCell(3).innerHTML= mvp[key].ramount
            row.insertCell(4).innerHTML= mvp[key].reason
            row.insertCell(5).innerHTML= mvp[key].rtime
            row.insertCell(6).innerHTML= mvp[key].rstatus
            row.insertCell(7).innerHTML= '<input type="text" id="message' + `${rowCount}` + '" placeholder="Optional" name="message"/>'
            row.insertCell(8).innerHTML= '<input type="button" value = "Approve" id="approve' + `${rowCount}` + '" onClick="Javacsript:Approve(this)"><input type="button" value = "Deny" id="deny' + `${rowCount}` + '" onClick="Javacsript:Deny(this)">'
        }}
}

disableEnterKey: function disableEnterKey(e){
        var key;
        if(window.event)
            key = window.event.keyCode; //IE
        else
            key = e.which; //firefox
        return (key != 13);
}

function Approve(obj) {
    let index = obj.parentNode.parentNode.rowIndex
    let table = document.getElementById("mvpTable")
    fetch('http://localhost:5000/mu', {
      method: 'POST',
      headers: {
        'content-type': 'application/json',
        authorization: 'Bearer 123abc456def'
               },
            body: JSON.stringify({"rid":`${table.rows[index].cells[0].innerHTML}`, "rstatus":"approved", "rmessage":`${table.rows[index].cells[7].firstChild.value}`})
    })
    location.reload();
}

function Deny(obj) {
    let index = obj.parentNode.parentNode.rowIndex
    let table = document.getElementById("mvpTable")
    fetch('http://localhost:5000/mu', {
      method: 'POST',
      headers: {
        'content-type': 'application/json',
        authorization: 'Bearer 123abc456def'
               },
            body: JSON.stringify({"rid":`${table.rows[index].cells[0].innerHTML}`, "rstatus":"denied", "rmessage":`${table.rows[index].cells[7].firstChild.value}`})
    })
    location.reload();
}

async function get_mvh() {
    let url = 'http://localhost:5000/mvh'
    let response = await fetch(url)
    // Remember that fetch returns a response object, not the direct response body
    let mvh = await response.json()
    if (mvh == "none"){window.location.replace('http://localhost:5000/ers.html') }
    else {
        for (let key in mvh) {
            let table = document.getElementById("mvhTable")
            var rowCount = table.rows.length
            var row = table.insertRow(rowCount)
            row.insertCell(0).innerHTML= key
            row.insertCell(1).innerHTML= mvh[key].ename
            row.insertCell(2).innerHTML= mvh[key].eid
            row.insertCell(3).innerHTML= mvh[key].ramount
            row.insertCell(4).innerHTML= mvh[key].reason
            row.insertCell(5).innerHTML= mvh[key].rstatus
            row.insertCell(6).innerHTML= mvh[key].rmessage
            row.insertCell(7).innerHTML= mvh[key].rtime
            row.insertCell(8).innerHTML= mvh[key].ctime
            row.insertCell(9).innerHTML= mvh[key].cname
        }}
}

// The window object has an onload property that we can use to perform operations as soon as the window loads:
// Please note that for immediately firing off functions, you can use IIFE (Immediately Invoked Function Expressions).
//IIFEs, which are anon functions, also help you avoid cluttering the global namespace.
window.onload = function () {
    this.get_mvp()
    this.get_mvh()
    this.get_stats()
    this.get_name()
}