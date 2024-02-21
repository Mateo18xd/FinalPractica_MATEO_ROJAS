var nodes = new vis.DataSet([
{id: 1, label: "Pozo Carapungo"},
{id: 2, label: "Pozo los Cipres"},
{id: 3, label: "Pozo Gran Colombia"},
{id: 4, label: "Pozo Loma Angora Grande"},
{id: 5, label: "Pozo Avenida Huayna Capac"},
{id: 6, label: "Pozo Colegio Bolivar"},
{id: 7, label: "Pozo la barra"},
{id: 8, label: "Pozo la alborada"},
{id: 9, label: "Pozo Santo Domingo"},
{id: 10, label: "Pozo Machala"},
{id: 11, label: "Pozo Portoviejo"},
{id: 12, label: "Pozo Latacunga"},
{id: 13, label: "Pozo Quito"},
{id: 14, label: "Pozo Riombanba"},
{id: 15, label: "Pozo San Luis"},
{id: 16, label: "Pozo Azogues"},
{id: 17, label: "Poso Turi"},
{id: 18, label: "Pozo El valle"},
{id: 19, label: "Pozo Nabon"},
{id: 20, label: "Pozo Cuenca"},
{id: 21, label: "Pozo Pasaje"},
{id: 22, label: "Pozo Santa Rosa"},
{id: 23, label: "Pozo San Agustin"},
{id: 24, label: "Pozo Catamayo"},
{id: 25, label: "Pozo Malacatos"},
{id: 26, label: "Pozo Saraguro"},
{id: 27, label: "Pozo Cajas"},
{id: 28, label: "Pozo Canar"},
{id: 29, label: "Pozo Tambo"},
{id: 30, label: "Pozo Otavalo"},
{id: 31, label: "Pozo Esteban Godoy"},
{id: 32, label: "Pozo Esteban Godoy"},
]);
var edges = new vis.DataSet([
]);
var container = document.getElementById("mynetwork");
      var data = {
        nodes: nodes,
        edges: edges,
      };
      var options = {};
      var network = new vis.Network(container, data, options);