const { createApp } = Vue
console.log("hola");
createApp({
    data(){
    return{
        datos:[],
        firstName: "" ,
        lastName:"",
        creationDate:"",
        loans:[],
        
        
    }
},

created(){
    this.loadData()

},
  methods: {
    loadData(){
        axios.get('http://localhost:8080/api/clients/current')
        .then(response => {
          // console.log(response)
            this.datos = response.data;
            this.loans = this.datos.loans
            console.log(this.datos);
          
        } )
         .catch(error => console.log(error));
    },
    
    logout(){
        Swal.fire({
            title: 'Are you sure that you want to log out',
            inputAttributes: {
                autocapitalize: 'off'
            },
            showCancelButton: true,
            confirmButtonText: 'Sure',
            showLoaderOnConfirm: true,
            preConfirm: (login) => {
                return axios.post('/api/logout')
                    .then(response => {
                        window.location.href="/web/html/index.html"
                    })
                    .catch(error => {
                        Swal.showValidationMessage(
                            "Request failed: ${error}"
                        )
                    })
            },
            allowOutsideClick: () => !Swal.isLoading()
        })
    }


     }


}).mount("#app")