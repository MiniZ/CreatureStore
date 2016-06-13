$("#gender_hidden").val($("#genderList").find(":selected").text());
$(document).ready(
    function() {
        $("#genderList").change(
            function() {
                $("#gender_hidden").val(
                    $("#genderList").find(":selected")
                        .text());
            });
    });

$("#country_hidden").val($("#countriesList").find(":selected").text());
$(document).ready(
    function() {
        $("#countriesList").change(
            function() {
                $("#country_hidden").val(
                    $("#countriesList").find(":selected")
                        .text());
            });
    });