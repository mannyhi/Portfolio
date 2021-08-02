async function get_orders() {
	let url = 'http://localhost:9000/orders/pending'
	let response = await fetch(url)
	let evp = await response.json()
	if (Object.keys(evp).length === 0) {
		let table = document.getElementById("evpTable")
		var rowCount = table.rows.length
		var row = table.insertRow(1)
		row.insertCell(0).innerHTML = "none"
		row.insertCell(1).innerHTML = "-"
		row.insertCell(2).innerHTML = "-"
		row.insertCell(3).innerHTML = "-"
		row.insertCell(4).innerHTML = "-"
		row.insertCell(5).innerHTML = "-"
	}
	for (let key in evp) {
		let table = document.getElementById("evpTable")
		var rowCount = table.rows.length
		var row = table.insertRow(rowCount)
		row.insertCell(0).innerHTML = evp[key].id.toString().padStart(4, "0"),
		row.insertCell(1).innerHTML = new Date(evp[key].timePlaced).toLocaleString('en-US', { dateStyle: 'medium', timeStyle: 'short' })
		row.insertCell(2).innerHTML = evp[key].items
		row.insertCell(3).innerHTML = '$' + evp[key].total.toFixed(2);
		row.insertCell(4).innerHTML = evp[key].status
		if (evp[key].status == 'order placed') {
			row.insertCell(5).innerHTML = '<input type="button" value = "ready" id="ready' + `${rowCount}` + '" onClick="Javacsript:Ready(this)">'
		}
		else {
			row.insertCell(5).innerHTML = '<input type="button" value = "complete" id="ready' + `${rowCount}` + '" onClick="Javacsript:Complete(this)">'
		}
	}
}

function Ready(obj) {
	let index = obj.parentNode.parentNode.rowIndex
	let table = document.getElementById("evpTable")
	fetch('http://localhost:9000/orders/status/' + `${table.rows[index].cells[0].innerHTML}`, {
		method: 'PATCH',
		headers: {
			'content-type': 'application/json',
			authorization: 'Bearer 123abc456def'
		},
		body: JSON.stringify({ "employeeId": "admin22", "status": "ready for pickup" })
	})
	setTimeout(location.reload.bind(location), 600);
}

function Complete(obj) {
	let index = obj.parentNode.parentNode.rowIndex
	let table = document.getElementById("evpTable")
	fetch('http://localhost:9000/orders/status/' + `${table.rows[index].cells[0].innerHTML}`, {
		method: 'PATCH',
		headers: {
			'content-type': 'application/json',
			authorization: 'Bearer 123abc456def'
		},
		body: JSON.stringify({ "employeeId": "admin22", "status": "completed" })
	})
	setTimeout(location.reload.bind(location), 600);
}

disableEnterKey: function disableEnterKey(e) {
	var key;
	if (window.event)
		key = window.event.keyCode; //IE
	else
		key = e.which; //firefox
	return (key != 13);
}

window.onload = function() {
	this.get_orders()
}