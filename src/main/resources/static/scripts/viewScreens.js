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

