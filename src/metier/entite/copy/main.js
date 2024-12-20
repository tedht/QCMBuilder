let questions = [];
let questionIndex = 0;
let score = 0;
// utilisé pour stocker les réponses et leur état
let etatReponses = [];
let notions;
let associations = [];

// charge le tableau questions depuis le questions.json
async function chargerQuestions() {
    fetch('questions.json')
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            questions = data;

            afficherNbQuestions();
            afficherNotions();
            afficherTempsAccueil();
        })
        .catch(error => {
            console.error("Erreur lors de la lecture du JSON :", error);
        });
}
chargerQuestions();


const contentAccueil = document.getElementById('accueil');
const titreChrono = document.getElementById('titre-chrono');
if(gererChrono()) {
    titreChrono.style.color = 'red';
    titreChrono.innerText = 'chronométré';
} else {
    titreChrono.innerText = 'non-chronométré';     
}

const startEval = document.getElementById('start-button').addEventListener('click', () => {
    contentAccueil.style.display = 'none';
    reinitialiserEtatReponses();
    afficherQuestions();
});


// Affiche le nombre de questions sur la page d'accueil
function afficherNbQuestions() {
    const questionNombres = document.getElementById('question-nombre');

    const difficultesQuestion = questions.reduce((acc, q) => {
        acc[q.difficulte] = (acc[q.difficulte] || 0) + 1;
        return acc;
    }, {});

    const totalQuestions = questions.length;

    const ordreDifficultes = ['très-facile', 'facile', 'moyen', 'difficile'];

    const details = ordreDifficultes
        .filter(difficulte => difficultesQuestion[difficulte]) 
        .map(difficulte => `${difficultesQuestion[difficulte]} ${gererCoulDifficulte(difficulte)}`)
        .join(", ");

    questionNombres.innerText = `${totalQuestions} questions dont ${details}`;
}   


// Affiche le temps en min:sec sur la page d'accueil
function afficherTempsAccueil() {
    const tempsTotal = document.getElementById('temps-total');
    let temps = 0;
    let minutes = 0;
    let secondes = 0;

    questions.forEach(q => {
        temps += q.temps;
    });

    minutes = Math.floor(temps / 60); 
    secondes = temps % 60; 

    tempsTotal.innerHTML = `${minutes}:${secondes < 10 ? '0' : ''}${secondes} minutes`;
}

function afficherNotions() {
    const notionQuestion = questions.reduce((acc, q) =>
			acc.includes(q.notion) ? acc : acc.concat(q.notion),
		[]);
    notions = notionQuestion.join(', ');
    const contentNotion = document.getElementById('notions');

    contentNotion.innerText = notions;  
}

// Sert surtout à passer de accueil à question et appelle afficherQuestion()
function afficherQuestions() {
    const contentQuestionnaire = document.getElementById('questionnaire');
    contentQuestionnaire.style.display = 'flex';
    afficherQuestion(questionIndex);

    const btnValider = document.getElementById('btn-valider');
    btnValider.addEventListener('click', function() {
        validerReponses(questionIndex);
    });

    initialiserEtatReponses();
    gererBoutons();
}


//Affiche la question selon l'index dans questions 
function afficherQuestion(index) {
    const questionContainer = document.querySelector('.question-section');
    const question = questions[index];
    const lettreDifficulte = gererCoulDifficulte(question.difficulte);  
    const notion = question.notion;

    const contentDifficulte = document.getElementById('difficulte');
    const contentNumber = document.getElementById('question-number');
    const contentNotion = document.getElementById('question-notion');
    const contentTitre = document.getElementById('question-title');
    const contentTemps = document.getElementById('question-time')
    

    contentDifficulte.className = 'cercle-difficulte';
    contentDifficulte.classList.add(question.difficulte);
    contentDifficulte.innerText = lettreDifficulte;

    contentNumber.innerText = `${question.id}/${questions.length}`;

    contentNotion.innerText = "[ " + notion + " ]";

    contentTitre.innerText = question.intitule;
    contentTitre.innerHTML += question.fichierComplementaire ? ' <button id="btn-info" class="nav-button" onclick="gererBtnInfo()">+ d\'infos</button>' : '';
    

    if(gererChrono())  {
        contentTemps.style.color = 'red';
        contentTemps.innerText = `Chrono : ${question.temps} secondes`;
    } else {
        contentTemps.innerText = `Temps estimé: ${question.temps} secondes`;
    }

    questionContainer.innerHTML = `${afficherReponses(index)}`;
    
    mettreAJourBarreDeProgression(index, questions.length);

    aRepondu(index);
    rechargerReponses(question);
}


function afficherFin() {
    const contentQuestionnaire = document.getElementById('questionnaire');
    const page = document.getElementById('accueil');


    contentQuestionnaire.style.display = 'none';
    page.style.display = 'inline';

    document.getElementById('p-score').style.display = 'flex'; 
    document.getElementById('p-temps').style.display = 'none';
    document.getElementById('start-button').style.display = 'none';
    
    let scoreMax = 0;
    questions.forEach(q => {
        scoreMax += q.note;
    });
 
    document.getElementById('titre-page').innerText = 'Évaluation terminée !';
    document.getElementById('score-total').innerText = score + '/' + scoreMax;
    
    reinitialiserEtatReponses();
}


function stringAfficherReponses(reponse, type) {
    switch (type) {
        case 'qcm':
            return `
            <label>
                <input type="checkbox" name="qcm" value="${reponse}">
                ${reponse}
            </label><br>`;    
        default:
            return `
            <label>
                <input type="radio" name="choix-unique" value="${reponse}">
                ${reponse}
            </label><br>`;
    } 
}


function afficherReponses(index) {
    const question = questions[index];
    const reponses = question.propositions;
    const { propositionsGauche, propositionsDroite, type } = question;

    if (!question || (!reponses && (!propositionsGauche || !propositionsDroite))) {
        console.error("Question ou propositions non définies.");
        return '';
    }

    let corpsQuestions = `<form id="qcm-form">`;

    // Gestion des différents types de questions
    if (type === "qcm" || type === "choix-unique" || type === "elimination") {
        reponses.forEach((reponse) => {
            corpsQuestions += stringAfficherReponses(reponse, type);
        });

        if (type === "elimination") {
            corpsQuestions += `
                <button type="button" id="eliminer-btn" class="nav-button" onclick="eliminerReponse(${index})">
                    Éliminer une réponse
                </button>`;
        }
    } else if (type === "association") {
        // Gestion spécifique pour les questions d'association
        corpsQuestions += `
            <div class="association-container">
                <div class="column gauche">
                    ${propositionsGauche.map((prop, idx) => `
                        <div class="item gauche-item" data-index="${idx}" data-value="${prop}">${prop}</div>
                    `).join('')}
                </div>
                <div class="column droite">
                    ${propositionsDroite.map((prop, idx) => `
                        <div class="item droite-item" data-index="${idx}" data-value="${prop}">${prop}</div>
                    `).join('')}
                </div>
            </div>
            <button type="button" id="btn-reinitialiser-asso" onClick="reinitialiserAssociationsEtSvg()" class="nav-button">Réinitialiser associations</button>`;
    } else {
        console.error("Type de question non supporté.");
    }

    corpsQuestions += `</form>`;

    // Appel de la méthode pour ajouter les listeners après le rendu
    setTimeout(() => ajouterListenersAssociation(), 0);

    return corpsQuestions;
}

// Fonction pour ajouter les listeners et gérer les associations
function ajouterListenersAssociation() {
    const gaucheItems = document.querySelectorAll('.gauche-item');
    const droiteItems = document.querySelectorAll('.droite-item');

    // Ajouter l'événement click sur les éléments de gauche
    gaucheItems.forEach(itemGauche => {
        itemGauche.addEventListener('click', () => {
            // Gestion de la sélection
            deselectionnerTous('.gauche-item');
            itemGauche.classList.add('selected');
            verifierEtAssocier(); // Vérifie si une association est possible
        });
    });

    // Ajouter l'événement click sur les éléments de droite
    droiteItems.forEach(itemDroite => {
        itemDroite.addEventListener('click', () => {
            // Gestion de la sélection
            deselectionnerTous('.droite-item');
            itemDroite.classList.add('selected');
            verifierEtAssocier(); // Vérifie si une association est possible
        });
    });
}

function dessinerTrait(itemGauche, itemDroite) {
    if (!itemGauche || !itemDroite) {
        console.error("Un des éléments est manquant !");
        return;
    }

    let svg = document.querySelector('#svg-traits');
    if (!svg) {
        svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
        svg.setAttribute("id", "svg-traits");
        svg.style.position = "absolute";
        svg.style.top = "0";
        svg.style.left = "0";
        svg.style.width = "100%";
        svg.style.height = "100%";
        svg.style.pointerEvents = "none";
        document.body.appendChild(svg);
    }

    const rectGauche = itemGauche.getBoundingClientRect();
    const rectDroite = itemDroite.getBoundingClientRect();

    const x1 = rectGauche.right;
    const y1 = rectGauche.top + rectGauche.height / 2;
    const x2 = rectDroite.left;
    const y2 = rectDroite.top + rectDroite.height / 2;

    const line = document.createElementNS("http://www.w3.org/2000/svg", "line");
    line.setAttribute("x1", x1);
    line.setAttribute("y1", y1);
    line.setAttribute("x2", x2);
    line.setAttribute("y2", y2);
    line.setAttribute("stroke", "black");
    line.setAttribute("stroke-width", "2");

    svg.appendChild(line);
}

// Fonction pour associer automatiquement si un élément de chaque colonne est sélectionné
function verifierEtAssocier() {
    const itemGauche = document.querySelector('.gauche-item.selected');
    const itemDroite = document.querySelector('.droite-item.selected');

    if (itemGauche && itemDroite) {
        // Enregistre l'association
        const association = {
            gauche: itemGauche.innerText,
            droite: itemDroite.innerText
        };
        associations.push(association);

        dessinerTrait(itemGauche, itemDroite);

        // Désactive les items associés pour éviter de les réutiliser
        itemGauche.classList.add('disabled');
        itemDroite.classList.add('disabled');

        // Réinitialise la sélection
        itemGauche.classList.remove('selected');
        itemDroite.classList.remove('selected');
    }
}


// Fonction pour désélectionner tous les éléments d'une colonne
function deselectionnerTous(selector) {
    const items = document.querySelectorAll(selector);
    items.forEach(item => item.classList.remove('selected'));
}


// Fonction pour réinitialiser toutes les associations et supprimer les lignes dessinées
function reinitialiserVariableAssociations() {
    associations = [];
}

function reinitialiserSvg() {
    const svg = document.getElementById('svg-traits');
    if (svg) {
        svg.remove();
    }
}

function reinitialiserAssociationsEtSvg() {
    reinitialiserVariableAssociations();
    reinitialiserSvg();
}


function convertirReponsesEnObjets(question) {
    const listeArrays = question.reponses;

    return listeArrays.map(array => ({
        gauche: array[0], 
        droite: array[1] 
    }));
}

function eliminerReponse(questionIndex) {
    const question = questions[questionIndex];

    if (!question.elimination || question.elimination.length === 0) {
        console.warn("Aucune réponse à éliminer.");
        return;
    }

    const reponseAEliminer = question.elimination.shift();
    const pointsPerdus = question.pointsPerdus.shift() || 0;

    const inputs = document.querySelectorAll(`#qcm-form input[type="radio"]`);

    inputs.forEach(input => {
        if (input.value === reponseAEliminer && !input.classList.contains('eliminee')) {
            input.parentElement.classList.add('eliminee'); 
            input.disabled = true; 
            input.parentElement.appendChild(document.createTextNode(` - Points perdus : ${pointsPerdus}`));

            let scoreMax = 0;
            questions.forEach(q => {
                scoreMax += q.note;
            });
            score -= pointsPerdus;
            document.getElementById('score-total').innerText = ` ${score}/${scoreMax}`;
        }
    });

    if (question.elimination.length === 0) {
        const button = document.getElementById('eliminer-btn');
        if (button) button.disabled = true;
    }
}


function recupererReponses(index) {
    const formulaire = document.getElementById('qcm-form');
    const question = questions[index];
    const type = question.type;

    let reponsesSelectionnees = [];

    if (type === "qcm") {
        const checkboxes = formulaire.querySelectorAll('input[type="checkbox"]:checked');
        reponsesSelectionnees = Array.from(checkboxes).map(checkbox => checkbox.value);
    } else if (type === "choix-unique") {
        const radio = formulaire.querySelector('input[type="radio"]:checked');
        if (radio) {
            reponsesSelectionnees = [radio.value];
        }
    } else if (type === "elimination") {
        // Récupère les réponses éliminées
        const reponsesEliminees = Array.from(formulaire.querySelectorAll('.eliminee input'))
                                        .map(input => input.value);

        // Récupère la réponse sélectionnée
        const inputSelectionne = formulaire.querySelector('input[type="radio"]:checked');
        if (inputSelectionne) {
            reponsesSelectionnees = [inputSelectionne.value];
        }

        return { reponsesSelectionnees, reponsesEliminees };
    }

    return { reponsesSelectionnees };
}


async function validerReponses(index) {
    const question = questions[index];
    const type = question.type;
    let messageErreur;

    // Récupère les réponses sélectionnées par l'utilisateur
    const { reponsesSelectionnees, reponsesEliminees } = recupererReponses(index);

    // Cas spécifique pour le type "association"
    if (type === "association") {
        const reponsesAssociees = convertirReponsesEnObjets(question);
        const reponsesAssocieesUtilisateur = associations; // Supposons que `associations` contient les réponses de l'utilisateur

        if (reponsesAssocieesUtilisateur.length !== reponsesAssociees.length) {
            alert('Veuillez faire toutes les associations avant de valider');
            return;
        }

        // Prépare les réponses correctes et celles de l'utilisateur sous forme de chaînes
        const reponsesCorrectes = reponsesAssociees.map(reponse => `${reponse.gauche} - ${reponse.droite}`).sort();
        const reponsesUtilisateur = reponsesAssocieesUtilisateur.map(reponse => `${reponse.gauche} - ${reponse.droite}`).sort();
        // Comparaison des réponses
        const bonneReponse = JSON.stringify(reponsesUtilisateur) === JSON.stringify(reponsesCorrectes);

        // Si la réponse est correcte, ajouter les points
        if (bonneReponse) {
            score += question.note;
        }

        // Mise à jour de l'état de la réponse
        etatReponses[index] = {
            repondu: true,
            corrige: bonneReponse,
            optionSelectionnee: reponsesAssocieesUtilisateur
        };
        reinitialiserSvg();
        
        // Affichage du feedback
        await afficherFeedback(bonneReponse, question, reponsesUtilisateur);

        // Passage à la question suivante ou fin
        questionIndex++;
        if (questionIndex < questions.length) {
            afficherQuestion(questionIndex);
        } else {
            afficherFin();
        }

        return; // Sortie pour éviter de continuer avec le reste du code dans cette fonction
    } else {
        // Pour les autres types de questions, vérifier si des réponses ont été sélectionnées
        if (reponsesSelectionnees.length === 0) {
            messageErreur = type === "elimination" ? 'Veuillez sélectionner une réponse après élimination' :
                            type === "choix-unique" ? 'Veuillez sélectionner une réponse' : 
                            'Veuillez sélectionner au moins une réponse';
            alert(messageErreur);
            return;
        }
    }
       

    // Calculer les points perdus si c'est une question d'élimination
    let pointsPerdus = 0;
    if (type === "elimination") {
        if (question.elimination) {
            reponsesEliminees.forEach((reponseEliminee) => {
                const indexElimination = question.elimination.indexOf(reponseEliminee);
                if (indexElimination !== -1) {
                    question.note -= question.pointsPerdus[indexElimination];
                }
            });
        }
    }

    // Vérifie si les réponses sont correctes pour d'autres types de questions
    const bonneReponse = JSON.stringify(reponsesSelectionnees.sort()) === JSON.stringify(question.reponses.sort());

    if (bonneReponse) {
        score += question.note;
    }

    // Mise à jour de l'état de la réponse
    etatReponses[index] = {
        repondu: true,
        corrige: bonneReponse,
        optionSelectionnee: reponsesSelectionnees,
        reponsesEliminees: reponsesEliminees,
        pointsPerdus: pointsPerdus
    };

    // Affichage du feedback
    await afficherFeedback(bonneReponse, question, reponsesSelectionnees, pointsPerdus);

    // Passage à la question suivante ou fin
    questionIndex++;
    if (questionIndex < questions.length) {
        afficherQuestion(questionIndex);
    } else {
        afficherFin();
    }
}



function afficherFeedback(bonneReponse, question, reponsesUtilisateur) {
    return new Promise((resolve) => {
        let feedbackPopup = document.querySelector('.feedback-popup');
        if (!feedbackPopup) {
            feedbackPopup = document.createElement('div');
            feedbackPopup.classList.add('feedback-popup');
            document.body.appendChild(feedbackPopup);
        }

        const reponseMessage = bonneReponse ? "Bonne réponse !" : "Mauvaise réponse.";
        const styleRep = bonneReponse ? 'green' : 'red';

        const feedbackMessage = bonneReponse ? question.feedback : `Essayez encore ! ${question.feedback}`;

        let scoreMax = 0;
        questions.forEach(q => {
            scoreMax += q.note;
        });

        feedbackPopup.innerHTML = `
            <div class="popup-content">
                <h2 style="color :${styleRep};">${reponseMessage}</h2>
                <p><strong>Réponse correcte :</strong> ${question.reponses.join(', ')}</p>
                <p><strong>Votre réponse :</strong> ${reponsesUtilisateur.join(', ')}</p>
                <p><strong>Feedback :</strong> ${feedbackMessage}</p>
                <p><strong>Note :</strong> ${score} / ${scoreMax} </p>
                <button id="fermerPopup" class="nav-button">Fermer</button>
            </div>
        `;

        feedbackPopup.style.position = 'fixed';
        feedbackPopup.classList.add('show');

        const fermerBtn = document.getElementById('fermerPopup');
        fermerBtn.addEventListener('click', () => {
            feedbackPopup.remove();
            resolve(); 
        }, { once: true }); 
    });
}


function initialiserEtatReponses() {
    etatReponses = questions.map(() => ({
        repondu: false,
        corrige: false,
        optionSelectionnee: []
    }));
}


function rechargerReponses(question) {
    const etatReponse = etatReponses[questionIndex]; // Récupère l'état sauvegardé de la réponse
    const reponseUtilisateur = etatReponse?.optionSelectionnee || []; // Réponses sauvegardées
    const reponsesEliminees = etatReponse?.reponsesEliminees || []; // Réponses éliminées (pour "elimination")
    const bonneReponse = JSON.stringify(reponseUtilisateur) === JSON.stringify(question.reponses);

    if (reponseUtilisateur.length > 0 || reponsesEliminees.length > 0) {
        const formulaire = document.getElementById('qcm-form');

        if (question.type === "qcm") {
            reponseUtilisateur.forEach(reponse => {
                const checkbox = formulaire.querySelector(`input[type="checkbox"][value="${reponse}"]`);
                if (checkbox) {
                    checkbox.checked = true;
                    colorierReponse(checkbox.parentElement, question.reponses.includes(reponse));
                }
            });
        } 
        else if (question.type === "choix-unique") {
            const radio = formulaire.querySelector(`input[type="radio"][value="${reponseUtilisateur?.[0]}"]`);
            if (radio) {
                radio.checked = true;
                colorierReponse(radio.parentElement, bonneReponse);
            }
        } 
        else if (question.type === "elimination") {
            const radios = formulaire.querySelectorAll('input[type="radio"]');
            radios.forEach(radio => {
                if (reponsesEliminees.includes(radio.value)) {
                    radio.parentElement.classList.add('eliminee');
                    radio.disabled = true;
                }
                if (reponseUtilisateur.includes(radio.value)) {
                    radio.checked = true;
                    colorierReponse(radio.parentElement, bonneReponse);
                }
            });
        }
        else if (question.type === "association") {
            // Gestion des réponses pour les associations
            document.getElementById('btn-reinitialiser-asso').style.pointerEvents = 'none';
            document.getElementById('btn-reinitialiser-asso').style.opacity = '0.5';

            reponseUtilisateur.forEach(association => {
                const itemGauche = document.querySelector(`.gauche-item[data-value="${association.gauche}"]`);
                const itemDroite = document.querySelector(`.droite-item[data-value="${association.droite}"]`);

                if (itemGauche && itemDroite) {
                    itemGauche.classList.add('selected');
                    itemDroite.classList.add('selected');
                    dessinerTrait(itemGauche, itemDroite); 
                    itemGauche.classList.add('disabled');
                    itemDroite.classList.add('disabled');
                }
            });
        }

        const btnFeedback = document.getElementById('btn-feedback');
        btnFeedback.addEventListener('click', async () => {
            await afficherFeedback(bonneReponse, question, reponseUtilisateur);
        });
    }
}


function colorierReponse(element, estCorrecte) {
    if (estCorrecte) {
        element.classList.add('correct');
        element.classList.remove('incorrect');
    } else {
        element.classList.add('incorrect');
    }
}


function reinitialiserEtatReponses() {
    etatReponses = []; 
}


// Fonction pour gérer l'affichage des fichiers
function gererBtnInfo() {
    // Récupérer la question actuelle via questionIndex
    const questionActuelle = questions[questionIndex];

    if (questionActuelle && questionActuelle.fichierComplementaire) {
        const fichier = questionActuelle.fichierComplementaire;

        // Créer un lien pour afficher le fichier
        const lien = document.createElement("a");
        lien.href = fichier.chemin;
        lien.target = "_blank"; // Ouvrir dans un nouvel onglet
        lien.click(); // Simuler un clic pour ouvrir la page
    } else {
        alert("Aucun fichier complémentaire disponible pour cette question.");
    }
}



// Gère suivant et précédent selon si c'est chrono ou pas
function gererBoutons() {   
    const btnPrecedent = document.getElementById('btn-precedent');
    if(gererChrono())  {
        btnPrecedent.style.pointerEvents = 'none'; 
        btnPrecedent.style.opacity = '0.5';  
    } else {
        btnPrecedent.addEventListener('click', () => {
            if (questionIndex <= questions.length - 1 && questionIndex > 0) {
                questionIndex--;
                reinitialiserAssociationsEtSvg();
                afficherQuestion(questionIndex);
            }
        })
    }

    const btnSuivant = document.getElementById('btn-suivant');
    btnSuivant.addEventListener('click', () => {
        if (questionIndex < questions.length - 1) {
            questionIndex++;
            reinitialiserAssociationsEtSvg();
            afficherQuestion(questionIndex);
        }
    });
}


// Gère si l'éval est chronométré ou pas
function gererChrono() {
    const appli = document.getElementById('appli');
    return appli.getAttribute("data-chrono") === 'true';
}


// Prend une difficulté en valeur et renvoie le char associé
function gererCoulDifficulte(difficulte) {
    switch (difficulte) {
        case 'très-facile': return 'TF';
        case 'facile':      return 'F';
        case 'moyen':       return 'M'; 
        case 'difficile':   return 'D';
        default:            return 'Inconnue';
    }
}

function aRepondu(questionIndex) {
    const repondu = etatReponses[questionIndex]?.repondu;
    document.getElementById('btn-valider').style.cssText = repondu ? 'pointer-events: none; opacity: 0.5;' 
                                                                    : 'pointer-events: auto; opacity: 1;';

    document.getElementById('btn-feedback').style.cssText = repondu ? 'pointer-events: auto; opacity: 1;' 
                                                                    : 'pointer-events: none; opacity: 0.5;';
}


function mettreAJourBarreDeProgression(questionIndex, totalQuestions) {
    const pourcentage = Math.round(((questionIndex + 1) / totalQuestions) * 100);

    const barreProgression = document.getElementById('progress-bar');
    barreProgression.style.width = pourcentage + '%';

    const texteProgression = document.getElementById('progress-text');
    texteProgression.textContent = `Question ${questionIndex + 1} sur ${totalQuestions} (${pourcentage}%)`;
}