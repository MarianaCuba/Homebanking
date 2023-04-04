const { createApp } = Vue
console.log("hola");
createApp({
    data(){
    return{
        datos:[],
        firstName: "" ,
        lastName:"",
        email:""
    }
},

created(){
    this.loadData()

},
  methods: {
    loadData(){
        axios.get('http://localhost:8080/api/clients')
        .then(response => {
          console.log(response)
            this.datos = response.data;
            console.log(this.datos);
        
        } )
         .catch(error => console.log(error));
    },
     addClient(){
        this.postClient();
     },
     postClient(){
        axios.post('http://localhost:8080/api/clients', {
            firstName: this.firstName,
            lastName: this.lastName,
            email : this.email
          })
          .then(function (response) {
            this.loadData();
          })
          .catch(function (error) {
            console.log(error);
          });
     }

    }


}).mount("#app")