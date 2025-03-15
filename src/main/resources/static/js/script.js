console.log("script loaded")

function setTheme(theme){
    localStorage.setItem("theme",theme);
}

function getTheme(){
    let theme = localStorage.getItem("theme");
    return theme ? theme : "light"
}

function changeTheme(){
    console.log("change theme called")
    let currentTheme = getTheme()
    let themeChangeBtn = document.querySelector('#theme_change_btn')
    document.querySelector('html').classList.add(currentTheme)
    themeChangeBtn.querySelector('span').textContent = opposite(currentTheme);
    themeChangeBtn.addEventListener("click",()=>{
        document.querySelector('html').classList.remove(currentTheme)
        themeChangeBtn.querySelector('span').textContent = currentTheme;
        if(currentTheme == "dark"){
            currentTheme = "light"
        }
        else{
            currentTheme = "dark"
        }
        setTheme(currentTheme)
        document.querySelector('html').classList.add(currentTheme)
    });
}

function opposite(theme){
    return theme == "light"? "dark": "light";
}
changeTheme()