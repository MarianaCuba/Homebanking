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
        accounts:[],
        accountType:"",
        loanID:[],
        cuota:0,
        totalPay:0,
        pays:"",
        nameLoan:"",
        selectPay:"",
        selectAccount:""

        
        
    }
},

created(){
    this.loadData()

},
  methods: {
    loadData(){
        //,{headers:{'accept':'application/xml'}}
        axios.get('http://localhost:8080/api/clients/current')
        .then(response => {
           console.log(response.data)
            this.datos = response.data;
            this.loans = this.datos.loans
            this.accounts = this.datos.accounts.filter(account => account.active);
            console.log(this.datos);
            console.log(this.loans);
          
        } )
         .catch(error => console.log(error));
    }, 
    logout(){
        Swal.fire({
            title: 'Are you sure that you want to log out',
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
    },
    creationAccount(){
        Swal.fire({
             title: 'Are you sure you want to create an account?',
             showCancelButton: true,
             confirmButtonText: 'Sure',
             showLoaderOnConfirm: true,
            preConfirm: (login) => {
                return axios.post('/api/clients/current/accounts',`accountType=${this.accountType}`)
                    .then(response => {
                        window.location.href="/web/html/accounts.html"
                    })
                    .catch(error => {
                        console.log(error);
                        Swal.fire({
                            icon: 'error',
                            title: 'Oops...',
                            text: error.response.data,
                          })
                    })
            },
            allowOutsideClick: () => !Swal.isLoading()
        })

    },
    deleteAccount(id){
        axios.put(`/api/clients/current/deleteaccount`, `id=${id}`) 
        .then(response => {
            window.location.href="/web/html/accounts.html"
        })
         .catch(error=>
         Swal.fire({
            icon: 'error',
            text: error.response.data,
            confirmButtonColor: "#7c601893",
        })
         )

    },
    payLoan() {
        Swal.fire({
            title: 'Are you sure that you want to pay the loan?',
            inputAttributes: {
                autocapitalize: 'off'
            },
            showCancelButton: true,
            confirmButtonText: 'Sure',
            confirmButtonColor: "green",
            preConfirm: () => {
                return axios.post('/api/loans/pay', `idLoan=${this.nameLoan}&amount=${this.selectPay}&account=${this.selectAccount}`)
                    .then(response => {
                        Swal.fire({
                            icon: 'success',
                            text: 'Payment Success',
                            showConfirmButton: false,
                            timer: 2000,
                        }).then(() => window.location.href = "/web/html/accounts.html")
                    })
                    .catch(error => {
                        Swal.fire({
                            icon: 'error',
                            text: error.response.data,
                            confirmButtonColor: "green",
                        })
                    })
            },
            allowOutsideClick: () => !Swal.isLoading()
        })
    },
     }


}).mount("#app")