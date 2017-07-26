
/************************* Create new users upload file ***********************/

$(document).ready(function(){
     $("#uploadUserfile").click(function (event) {
         event.preventDefault();
         if($("#userfileUpload").val()=='') {
            alert("No file selected. Please select one to upload !");
         }
         else{
                // Get form
                var form = $('#userfileUploadForm')[0];

                // Create an FormData object
                var data = new FormData(form);


                // disabled the submit button
                $("#uploadUserfile").prop("disabled", true);

                $.ajax({
                    type: "POST",
                    enctype: 'multipart/form-data',
                    url: "/user/upload",
                    data: data,
                    processData: false,
                    contentType: false,
                    cache: false,
                    timeout: 600000,
                    success: function (data) {

                        $("#result").text(data);
                        console.log("SUCCESS : ", data);
                        $("#uploadUserfile").prop("disabled", false);

                    },
                    error: function (e) {

                        $("#result").text(e.responseText);
                        console.log("ERROR : ", e);
                        $("#btnSubmit").prop("disabled", false);

                    }
                });
         }
    });

});

/* function validateFileExtension(file) {

    var ext = file.split(".");
    ext = ext[ext.length-1].toLowerCase();
    if (ext!="csv") {
        alert("Only csv files are allowed");
        $("#userfileUpload").val("");
    }
    }
 */

 /************************ Create new ticket upload file **************************/
 /* $(document).ready(function($) {
          $(".table-row").click(function() {
              window.location = $(this).data("href");
          });
      });
*/

function validateFileExtension(file) {
    var ext = file.split(".");
    ext = ext[ext.length-1].toLowerCase();
    if (ext!="csv") {
        alert("Only csv files are allowed");
        $("#fileUpload").val("");
    }
    }

$(document).ready(function(){
     $("#uploadFile").click(function (event) {
         //stop submitting the form, we will post it manually.
          event.preventDefault();
         if($("#fileUpload").val()=='') {
            alert("No file selected. Please select one to upload !");
         }
         else{
            // Get form
            var form = $('#fileUploadForm')[0];
            // Create a FormData object
            var data = new FormData(form);
            // disabled the submit button
            $("#uploadFile").prop("disabled", true);
            $.ajax({
                type: "POST",
                enctype: 'multipart/form-data',
                url: "/ticket/upload",
                data: data,
                processData: false,
                contentType: false,
                cache: false,
                timeout: 600000,
                success: function (data, status) {
                    $("#result").text(data);
                    $("#uploadFile").prop("disabled", false);
                    $('#result').removeClass("hide").addClass("show");
                    $('#uploadFile').removeClass("show").addClass("hide");
                    $('#uploadPath').removeClass("show").addClass("hide");
                },
                error: function (e) {
                    $("#result").text(e.responseText);
                    console.log("ERROR : ", e);
                    $("#btnSubmit").prop("disabled", false);
                }
            });
         }
    });
});

/*********************** Name formatter to format the ticket name into an href ************************************/
function nameFormatter(value){
    return '<a href="#" onclick="eachTicket(\''+value+'\');">' + value + '</a>';

}

function eachTicket(value){
    $.ajax({
        type: "GET",
        url: "/ticket?name=" + value,
        dataType:"json",
        timeout: 600000,
        success: function (data, status) {
            // below code is because there is a bug in server side rendering of json - whole array is rendered
            // instead of just the one element clicked. Needs to be fixed on server side code.
            var mydata = data[0];
            console.log("data", data);
            console.log(mydata);
            // end of hack code to work around server side json rendering bug. Needs to be fixed.

            //populating the pop-up window
            $("#myModal").modal();
            $('#ticket-details > p#ticketId').text("TicketID:" + mydata.id);
            $('#ticket-details > p#ticketName').text(mydata.name);
            $('#ticket-details > p#ticketPriority').text(mydata.priority);
            $('#ticket-details > p#ticketStatus').text(mydata.status);
            $('#ticket-details > p#ticketAssignee').text(mydata.assignedTo);
        },
        error: function (e) {
            console.log("ERROR");
        }
    });
}