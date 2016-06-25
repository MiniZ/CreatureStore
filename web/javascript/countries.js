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