let photos = /*[[${keys}]]*/ [];

function updatePhoto() {
    let urlBase = /*[[${urlBase}]]*/ "";
    let storage = /*[[${storage}]]*/ "";
    let key = photos[Math.floor(Math.random() * photos.length) % photos.length];
    document.getElementById("bg").style.backgroundImage = 'url(' + urlBase + '/' + storage + '/' + key + ')';
}

document.addEventListener('DOMContentLoaded', function () {
    updatePhoto();
    window.setInterval(updatePhoto, 5000);
});
