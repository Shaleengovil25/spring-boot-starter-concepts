console.log("admin js loaded")

document.querySelector("#image_file_input")
    .addEventListener('change',function(e){
        let file = e.target.files[0]
        let reader = new FileReader()
        reader.onload = function(){
            document.querySelector("#upload_image_preview")
                .setAttribute("src",reader.result)
        }
        reader.readAsDataURL(file)
    })