const { createApp } = Vue
console.log("hola");
createApp({
    data(){
    return{
        selectColor:"",
       selectType:"",
        
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
          text: error.response.data,
          
     
        })
)},
creationCard(){  
       Swal.fire(
            'Good job!',
            'You clicked the button!',
            'success'
          )
    axios.post('/api/clients/current/cards', `type=${this.selectType}&color=${this.selectColor}`)
        .then(response => window.location.href ="/web/html/cards.html")
 
        .catch(error => 
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: error.response.data,
              })
        )
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
    // Swal.fire({
    //     title: 'Are you sure that you want to log out',
    //     inputAttributes: {
    //         autocapitalize: 'off'
    //     },
    //     showCancelButton: true,
    //     confirmButtonText: 'Sure',
    //     showLoaderOnConfirm: true,
    //     preConfirm: () => {
    //         return axios.post('/api/clients/current/cards',{
    //             color:this.selectColor,
    //             type:this.selectType
    //         } )
    //             .then(response => window.location.href ="/web/html/accounts.html")
    //             .catch(error => 
    //                 console.log(error),
    //                 Swal.fire({
    //                     icon: 'error',
    //                     title: 'Oops...',
    //                     text: error.response.data,
    //                   })
    //             )
    //             .catch(error => {
    //                 Swal.showValidationMessage(
    //                     "Request failed: ${error}"
    //                 )
    //             })
    //     },
    //     allowOutsideClick: () => !Swal.isLoading()
    // })

}
    


}).mount("#app")