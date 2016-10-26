function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
    document.getElementById("main").style.marginLeft = "250px";
    document.getElementById("main2").style.marginLeft = "250px";
}

function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
    document.getElementById("main").style.marginLeft = "0";
    document.getElementById("main2").style.marginLeft = "0";
}

// Get the modal
var modal = document.getElementById('myModal');

var lockbtn = document.getElementById('lockInput');
var address = document.getElementById("address");
var cadastral = document.getElementById("cadastral");
var zipcode = document.getElementById("zipcode");
var builtYear = document.getElementById("builtYear");
var area = document.getElementById("area");
var city = document.getElementById("city");
var condition = document.getElementById("condition");
var extraText = document.getElementById("extraText");
var isLocked = true;



// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal
btn.onclick = function() {
    modal.style.display = "block";
};

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
};

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
};
