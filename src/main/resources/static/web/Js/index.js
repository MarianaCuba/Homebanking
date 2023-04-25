const { createApp } = Vue
console.log("hola");
createApp({
    data(){
    return{
       email:"",
       password:"",
        
    }
},

  methods: {
    signIn(){
        axios.post('/api/login','email=' + this.email + "&password=" + this.password)
        .then(response => window.location.href ="/web/html/accounts.html")
        
        .catch(error =>
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: "email or password",
          
     
        })
)}

     }


}).mount("#app")