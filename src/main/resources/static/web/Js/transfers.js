const { createApp } = Vue

createApp({
    data() {
        return {
            // Inicializamos las variables
            data: [],
            description : "",
            account : "",
            amount : "",
            destinateAccount: "",
            
        }
    },
    created() {
        this.loadData()
    },
    methods: {
        loadData() {
            axios.get('http://localhost:8080/api/clients/current')
                .then(response => {
                    this.data = response.data
                    console.log(this.data);

                })
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
        },
        createTransactions(){
            Swal.fire({
                title: 'Are you sure that you want to transfer this amount to this account',
                inputAttributes: {autocapitalize: 'off'},
                showCancelButton: true,
                confirmButtonText: 'Sure',
                confirmButtonColor: "rgb(16, 204, 88)",
                preConfirm: () => {
                    return axios.post('/api/clients/current/transactions', `amount=${this.amount}&description=${this.description}&initialAccount=${this.account}&destinateAccount=${this.destinateAccount}`)
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
            )},
                allowOutsideClick: () => !Swal.isLoading()
            })
            .catch(error => {console.log(error)})
        },
    },

}).mount("#app");

