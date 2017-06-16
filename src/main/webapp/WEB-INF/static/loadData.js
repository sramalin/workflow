// specify the columns
var columnDefs = [
    {headerName: "Ticket Id", field: "ticketId"},
    {headerName: "Ticket Name", field: "ticketName"},
    {headerName: "Ticket Status", field: "ticketStatus"},
    {headerName: "Assigned To", field: "assignedTo"}
];

// specify the data
var rowData = [
    {tickedId: "Toyota", ticketName: "Celica", ticketStatus: 35000,assignedTo: ""},
    {tickedId: "Ford", ticketName: "Mondeo", ticketStatus: 32000, assignedTo: ""},
    {tickedId: "Porsche", ticketName: "Boxter", ticketStatus: 72000, assignedTo: ""}
];

// let the grid know which columns and what data to use
var gridOptions = {
    columnDefs: columnDefs,
    rowData: rowData, enableSorting: true,

    // enable filtering
    enableFilter: true,
    onGridReady: function () {
        gridOptions.api.sizeColumnsToFit();

    }

};

// wait for the document to be loaded, otherwise ag-Grid will not find the div in the document.
document.addEventListener("DOMContentLoaded", function () {

    // lookup the container we want the Grid to use
    var eGridDiv = document.querySelector('#myGrid');

    // create the grid passing in the div to use together with the columns & data we want to use
    new agGrid.Grid(eGridDiv, gridOptions);
});