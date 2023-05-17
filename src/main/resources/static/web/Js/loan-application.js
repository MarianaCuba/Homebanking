const {createApp} = Vue

createApp ({
    data(){
        return{
            data:[],
            dataLoan:[],
            checked:"",
            dataSelect:[],
            amount:"",
            selectPayments:"",
            destinateAccount:"",
            dataSelectPayment:[],
            idLoan:"",
            amountInteres: 0,
            cuotas: 0,
            totalAmountInteres:0
        }
    },
    created(){
        this.loadData()
        this.datosLoan()
    },
    methods:{
        loadData() {
            axios.get('http://localhost:8080/api/clients/current/accounts')
                .then(response => {
                    this.data = response.data
                    console.log(this.data);

                })
                .catch(error => console.log(error));
        },
        datosLoan(){
            axios.get('http://localhost:8080/api/loans')
            .then(response => {
                this.dataLoan = response.data
                console.log(this.dataLoan);
                // this.loanSelect()
               
            })
            // .catch(error => console.log(error))
        },
        createLoan(){
        
            this.idLoan = this.dataSelect.id
             
             // Swal.fire({
                  
                //   preConfirm: () => {
                //       return 
                      axios.post('/api/loans', {
                         id : this.idLoan,
                         amount : this.amount,
                         payments : this.selectPayments,
                         numberAccountDestinate : this.destinateAccount
                     })
                         .then(response =>
                             Swal.fire({
                                 icon: 'success',
                                 text: 'Transaction succesfully',
                                 showConfirmButton: false,
                                 timer: 2000,
                             })
                             .then( () => window.location.href="/web/html/accounts.html")

                         .catch(error => {
                              Swal.fire({
                                  icon: 'error',
                                  text: error.response.data,
                                  confirmButtonColor: "rgb(16, 204, 88)",
                              })
                          })
              )
           // },
                  allowOutsideClick: () => !Swal.isLoading()
              //})
              .catch(error => {console.log(error)})
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
        },
       
    

    },
    computed:{
           loanSelect(){
            this.dataSelect = this.dataLoan.find( loan => loan.name == this.checked)
            
           console.log(this.dataSelect);
           if(this.dataSelect){
           this.idLoan = this.dataSelect.id }
           console.log(   
             this.idLoan,
             this.amount,
             this.selectPayments,
             this.destinateAccount);
            
        },
         interes(){
                this.amountInteres = this.amount *0.2
                this.totalAmountInteres = this.amountInteres + this.amount
                this.cuotas = this.totalAmountInteres / this.selectPayments 
                console.log(this.cuotas);
                console.log(this.amountInteres);
        }
        // dataLoanId(){
        //     this.idLoan = this.dataSelect.id
        //     console.log(this.idLoan);
        // }
    }
    
}).mount("#app");