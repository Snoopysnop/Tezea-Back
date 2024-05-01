
# Les environnements

## Développement

L'environnement de développement est propre à chacun et utilise la machine locale.

### Initialiser l'environnement de développement

1. Créer la base de données
Pour créer la base de données, il faut disposer d'un service mysql sur le port 3306

Il faut ensuite créer l'utilisateur et la table en jouant les commandes sql suivantes :

```sql
DROP DATABASE IF EXISTS tezea;
DROP USER IF EXISTS 'tezeaAdmin'@'localhost';
CREATE database tezea;
CREATE USER IF NOT EXISTS 'tezeaAdmin'@'localhost' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON tezea.* TO 'tezeaAdmin'@'localhost';
FLUSH PRIVILEGES;
```

Il est également possible de se connecter à la base de données de qualification, pour avoir des données partagées et ne pas avoir besoin d'avoir mysql.

Pour cela, il faut modifier la variable spring.profiles.active dans le fichier [properties](src/main/resources/application.properties) et remplacer `spring.profiles.active=dev` par `spring.profiles.active=qa`

/!\ Pour éviter les étourderies, ne pas push cette modification.


### Lancer l'application

Pour lancer l'application en local, il faut d'abord générer les fichiers source :
`mvn clean install`

Puis lancer le [main](src/main/java/fr/isitc/tezea/TezeaApplication.java) de l'application.

Une fois l'application en cours d'exécution, le swagger est disponible [ici](http://localhost/swagger-ui/index.html).

## Qualification

L'environnement de qualification tourne sur le serveur 148.60.11.163
L'application est redéployée à chaque modification de la branche main.
Il est également possible de déployer une autre branche, en créant une [pipeline](https://gitlab.istic.univ-rennes1.fr/tezea/back/-/pipelines/new) sur la branche choisie, et en lançant manuellement les jobs build puis deploy.

/!\ L'environnement de qualification différera de la branch main après avoir lancé le déploiement depuis un autre branche.
Il faut lancer une nouvelle pipeline depuis la branche main pour restaurer l'environnement (il n'y a pas besoin de lancer les jobs manuellement sur la branche main).

Le swagger de l'environnement de qualification est disponible [ici](http://148.60.11.163/swagger-ui/index.html).