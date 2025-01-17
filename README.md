
# QCM Builder

## Table des matières

- [QCM Builder](#qcm-builder)
	- [Table des matières](#table-des-matières)
	- [Description](#description)
	- [Fonctionnalités](#fonctionnalités)
	- [Prérequis](#prérequis)
	- [Lancer l'application](#lancer-lapplication)
		- [Sous Linux](#sous-linux)
		- [Sous Windows](#sous-windows)
	- [Exemple d’utilisation](#exemple-dutilisation)
	- [Auteurs](#auteurs)
	- [Rôles de l'équipe](#rôles-de-léquipe)


## Description

Cette application se compose de deux parties : une partie professeur et une partie étudiant.

- **Partie professeur :** Permet aux enseignants de générer des Questionnaires à partir des questions qu'ils ont créées, en tenant compte de critères spécifiques.

- **Partie étudiant :** Donne aux élèves accès aux Questionnaires via l'intranet pédagogique utilisé par les enseignants, leur permettant de s'autoévaluer.

## Fonctionnalités

- **Architecture MVC :** Cette application utilise une structure Modèle-Vue-Contrôleur (MVC).

- **Types de questions :** 

	- **Question à réponses multiples :** L'étudiant choisit une ou plusieurs réponses parmi une liste de propositions.

	- **Question à réponse unique :** L'étudiant choisit une unique réponse parmi plusieurs réponses.

	- **Association :** L'étudiant doit relier chaque proposition avec une unique réponse. Chaque proposition a une réponse associée.

	- **Elimination :** Cette question est semblable à la question à réponse unique. Cependant, elle offre la possibilité d'enlever des propositions afin d'offrir un choix plus restreint à l'étudiant sous contrainte de se voir retirer une partie des points.

- **Pièces jointes :** Le professeur peut rajouter une pièce jointe accompagnant la question (un document, du code...).

## Prérequis
- Java 17 ou version supérieure.

## Lancer l'application
### Sous Linux
```bash
chmod u + x run.sh
./run.sh
```

### Sous Windows
```bash
run.bat
```
Vous pouvez également lancer l'application avec un double clic sur le fichier run.sh ou run.bat.

## Exemple d’utilisation

...

## Auteurs

- **Développeurs :**
  - Ted HERAMBERT
  - Lucas LANGLOIS
  - Felix LE GARS
  - Kylian LUCAS-THUILLIER
  - Thibault MANGEANT

- **Employeur :** PromInfo (Collectif de professeurs de l'IUT du Havre)

## Rôles de l'équipe
**Partie professeur**
- Développement backend
  - Lucas LANGLOIS
  - Felix LE GARS
  - Kylian LUCAS-THUILLIER
  - Thibault MANGEANT
- Développement frontend
  - Ted HERAMBERT
- Design
  - Ted HERAMBERT

**Partie étudiant**
- Développement frontend
  - Lucas LANGLOIS
- Design
  - Lucas LANGLOIS

**Documentation**
- Diagramme de classes
  - Thibault MANGEANT
  - Felix LE GARS
  - Kylian LUCAS-THUILLIER
- README
  - Thibault MANGEANT
  - Félix LE GARS
- LICENSE
  - Thibault MANGEANT
- Javadoc
  - Kylian LUCAS-THUILLIER
  - Félix LE GARS