async function get_stats() {
	let url = 'http://localhost:9000/orders/stats'
	let response = await fetch(url)
	let stats = await response.json()
	let table = document.getElementById("statsTable")
	var rowCount = table.rows.length
	var row = table.insertRow(rowCount)
	row.insertCell(0).innerHTML = stats["Q1 Revenue"]
	row.insertCell(1).innerHTML = stats["Q2 Revenue"]
	row.insertCell(2).innerHTML = stats["Q3 Revenue"]
	row.insertCell(3).innerHTML = stats["Q4 Revenue"]
	row.insertCell(4).innerHTML = stats["Total Revenue"]
}

async function get_orders() {
	let url = 'http://localhost:9000/orders'
	let response = await fetch(url)
	let orders = await response.json()
	var tableData = [];
	for (let key in orders) {
		var action = 'refunded'
		if (orders[key].status != 'canceled') {
			action = '<input type="button" value = "cancel" id="cancel" onClick="Javacsript:Cancel(this)">'
		}
		tableData.push({
			'oid': orders[key].id.toString().padStart(4, "0"),
			'timeplaced': new Date(orders[key].timePlaced).toLocaleString('en-US', { dateStyle: 'medium', timeStyle: 'short' }),
			'items': orders[key].items,
			'total': '$' + orders[key].total.toFixed(2),
			'status': orders[key].status,
			'action': action
		});
	}
	return Promise.resolve(tableData)
}

function Cancel(obj) {
	let index = obj.parentNode.parentNode.rowIndex
	let table = document.getElementById("ordersTable")
	fetch('http://localhost:9000/orders/cancel/' + `${table.rows[index].cells[0].innerHTML}`, {
		method: 'PATCH',
		headers: {
			'content-type': 'application/json',
			authorization: 'Bearer 123abc456def'
		},
		body: JSON.stringify({ "employeeId": "admin22", "notes": "canceled by customer" })
	})
	setTimeout(location.reload.bind(location), 600);
}

var tableData
var state

get_orders().then((value) => { main(value) });

function main(value) {
	tableData = value
	console.log(typeof value + " value")
	console.log(value)
	state = {
		'querySet': tableData,
		'page': 1,
		'rows': 15,
		'window': 5,
	}
	buildTable()
}

function pagination(querySet, page, rows) {
	var trimStart = (page - 1) * rows
	var trimEnd = trimStart + rows
	var trimmedData = querySet.slice(trimStart, trimEnd)
	var pages = Math.round(querySet.length / rows);
	return {
		'querySet': trimmedData,
		'pages': pages,
	}
}

function pageButtons(pages) {
	var wrapper = document.getElementById('pagination-wrapper')
	wrapper.innerHTML = ``
	console.log('Pages:', pages)
	var maxLeft = (state.page - Math.floor(state.window / 2))
	var maxRight = (state.page + Math.floor(state.window / 2))
	if (maxLeft < 1) {
		maxLeft = 1
		maxRight = state.window
	}
	if (maxRight > pages) {
		maxLeft = pages - (state.window - 1)
		if (maxLeft < 1) {
			maxLeft = 1
		}
		maxRight = pages
	}
	for (var page = maxLeft; page <= maxRight; page++) {
		wrapper.innerHTML += `<button value=${page} class="page btn btn-sm btn-info">${page}</button>`
	}
	if (state.page != 1) {
		wrapper.innerHTML = `<button value=${1} class="page btn btn-sm btn-info">&#171; First</button>` + wrapper.innerHTML
	}
	if (state.page != pages) {
		wrapper.innerHTML += `<button value=${pages} class="page btn btn-sm btn-info">Last &#187;</button>`
	}
	$('.page').on('click', function() {
		$('#table-body').empty()
		state.page = Number($(this).val())
		buildTable()
	})

}

function buildTable() {
	var table = $('#table-body')
	var data = pagination(state.querySet, state.page, state.rows)
	var myList = data.querySet
	for (var i = 1 in myList) {
		var row = `<tr>
                  <td>${myList[i].oid}</td>
                  <td>${myList[i].timeplaced}</td>
                  <td>${myList[i].items}</td>
                  <td>${myList[i].total}</td>
                  <td>${myList[i].status}</td>
                  <td>${myList[i].action}</td>
                  `
		table.append(row)
	}
	pageButtons(data.pages)
}

window.onload = function() {
	this.get_stats()
	this.get_orders()
}