document.onreadystatechange = function () {
    if (document.readyState == "complete") {
        var searchBtn = document.querySelector("#modPwd");
        searchBtn.addEventListener("click", function (event){checkPassword(event)});

        function checkPassword(e) {
            var nuova = document.getElementById('nuova').value;
            var ripetizione = document.getElementById('nuova_check').value;
            alert(nuova +" "+ripetizione);
            if(nuova != ripetizione){
                e.preventDefault();
                alert("Le nuove password inserite sono diverse!");
            }
        }
    }
}