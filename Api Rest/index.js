const { debug } = require("console");
const express = require ("express");
const mongoose = require ("mongoose");
const url = require("url");
mongoose.connect('mongodb://localhost:27017/PSP');
let marvelSchema = new mongoose.Schema({
    nombre: String,
    poder: String,
    alias: String
});
const Marvel = mongoose.model('Marvel', marvelSchema, 'Marvel');
let app = express();
app.use(express.json());
app.listen(8080);


app.get('/heroes', (req, res) => {
    if (req.query.id){
        //método GET para obtener información sobre un héroe, buscando por id
        var url_parts = url.parse(req.url, true);
        console.log(url_parts.query.id);
        Marvel.findById(url_parts.query.id)
            .then(heroe => {
        if(!heroe) {
            return res.status(404).send("Héroe no encontrado");
        }
        res.send(heroe);
        })
            .catch(err => {
        console.error("Error fetching hero data: ", err);
        res.status(500).send("Internal Server Error");
    });
    }
    //método GET para obtener la información sobre los héroes Marvel
    else{
            Marvel.find()
        .then(heroes => {
            res.send(heroes);
        }).catch(err => {
            console.error("Error fetching data: ", err);
            res.status(500).send("Internal Server Error");
        });
    }
});
//método para obtener héroes con la aparicion en una pelicula y con un determinado alias
app.get('/heroes/:nombre/:alias', (req, res) => {
    var nombre = req.params.nombre;
    var alias = req.params.alias;
    console.log(nombre + " - " + alias);
    Marvel.find({nombre: nombre, alias: alias})
    .then(heroes =>{
        console.log(heroes);
        if(heroes.length === 0){
            res.status(404).send("No hay ninguna coincidencia con los parametros buscados");
        }else{
            res.send(heroes);
        }
    })
    .catch(err => {
        res.status(500).send("Error interno del servidor");
    });
});
//método para insertar un nuevo héroe
app.post('/heroes', (req, res) => {
    try {
        const nuevoHeroe = new Marvel({
            nombre: req.body.nombre,
            alias: req.body.alias,
            poder: req.body.poder
        }
        );
        nuevoHeroe.save()
        .then(() => {
        res.status(201).json(nuevoHeroe); //("Datos insertados correctamente");
        console.log(req.body);
        })
        .catch(err => {
            console.error("Error al insertar datos:", err);
                res.status(500).send("Error interno del servidor");
        });
    } catch (error) {
        console.error("Error al insertar datos:", error);
        res.status(500).send("Error interno del servidor");
    }
});
//método para actualizar un héroe
app.put('/heroes/:id', (req, res) => {
    var id = req.params.id;
    console.log(req.body);

    Marvel.findByIdAndUpdate(id, req.body, { new: true })
        .then(heroeActualizado => {
            if (!heroeActualizado) {
                return res.status(404).send("Héroe no encontrado");
            }
            res.status(200).json(heroeActualizado); // Devolvemos el héroe actualizado
        })
        .catch(err => {
            console.error("Error al actualizar el héroe:", err);
            res.status(500).send("Error interno del servidor");
        });
});
//método para eliminar un héroe
app.delete('/heroes/:id', (req, res) => {
    const id = req.params.id;

    Marvel.findByIdAndDelete(id)
        .then(heroeEliminado => {
            if (!heroeEliminado) {
                return res.status(404).send("Héroe no encontrado");
            }
            res.status(200).send("Héroe eliminado correctamente");
        })
        .catch(err => {
            console.error("Error al eliminar el héroe:", err);
            res.status(500).send("Error interno del servidor");
        });
});