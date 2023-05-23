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

        .then(response =>
        { if(this.email == "mari04@gmail.com"){
          window.location.replace('http://localhost:8080/manager.html');
        }else{
          window.location.href='/web/html/accounts.html';
          Swal.fire({
            icon: 'success',
            title: 'Login successful!',
            showCancelButton: true,
            confirmButtonText: 'Ok',
            cancelButtonText: 'Cancel',
            timer: 6000,
        })
        }
         
    })
        .catch(error =>
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: error.response.data,
          
     
        })
)}

     },


}).mount("#app")