 /** Create new users upload file **/

$(document).ready(function(){
     $("#uploadFile").click(function (event) {
         event.preventDefault();
         if($("#fileUpload").val()=='') {
            alert("No file selected. Please select one to upload !");
         }
         else{
                // Get form
                var form = $('#fileUploadForm')[0];

                // Create an FormData object
                var data = new FormData(form);


                // disabled the submit button
                $("#uploadFile").prop("disabled", true);

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
                        $("#uploadFile").prop("disabled", false);

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

function validateFileExtension(file) {

    var ext = file.split(".");
    ext = ext[ext.length-1].toLowerCase();
    if (ext!="csv") {
        alert("Only csv files are allowed");
        $("#fileUpload").val("");
    }
    }


 /** Create new ticket upload file **/
 $(document).ready(function($) {
          $(".table-row").click(function() {
              window.location = $(this).data("href");
          });
      });

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
                    console.log("SUCCESS : ", data);
                    $("#uploadFile").prop("disabled", false);
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
