const { createApp } = Vue
console.log("hola");
createApp({
    data(){
    return{
        firstName:"",
        lastName:"",
        email:"",
        password:"",
        
    }
},

  methods: {
    register(){
        axios.post('/api/clients','firstName='+ this.firstName +'&lastName=' + this.lastName +'&email=' + this.email + "&password=" + this.password)
        .then(response => 
            axios.post('/api/login','email=' + this.email + "&password=" + this.password)
            .then(response => window.location.href ="/web/html/accounts.html")
            )
         
        .catch(error => 
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: error.response.data,
                
           
              })
           )
    }

     }


}).mount("#app")