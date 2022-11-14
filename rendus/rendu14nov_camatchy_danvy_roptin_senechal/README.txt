Groupe :
	21801009 Alexander Camatchy
	21914304 Alix Danvy
	22005759 Mathis Roptin
	22003660 Félix Sénéchal 
	
Mode d'emploi:
	Note: pour compiler correctement, il est nécessaire d'avoir
		un répertoire lib contenant la librairie de tests fourni 
		(à côté du répertoire src)
		
	script ant:
		$ ant : pour compiler le projet.
		$ ant compile : pour compiler le projet.
		$ ant doc : pour générer la documentation du projet.
		$ ant test : pour lancer les tests sur tous 
				les éléments du projet.
		$ant benchminer :  pour lancer les benchmark de comparaison
				entre Apriori et FPGrowth.
				notez que: il est nécessaire d'avoir créer le répertoire
				'benchmark_results'. (résultats sous forme de csv)
		$python3 scripts/plotmaker.py : pour transormer les résultats du
				benchmark réalisé précedement en graphiques.

Note:
	Les seules classes executable nécessaire jusque là sont celles 
	lançant les tests ou le benchmark: 	test.Test ,
										test.TestFPGrowth et 
										BenchmarkItemsetMiner
	
	Nous avont réalisé en plus de ce qui était demandé un ItemsetMiner 
	FPGrowth en créant FPTree et FPNode pour le faire fonctionner.
	Ainsi qu'un benchmark pour le comparer avec Apriori.
	Nous commentons rapidement les résultats obtenus dans la partie ci-dessous.
	
	Toutes les informations nécessaires à la compréhenssion du code
	sont disponible dans la documentation fourni. 
	(sous forme de commentaire dans le code directement 
	ou en générant la java-doc)


Résultats du benchmark:
	Disclaimer: les résultats obtenus ne sont pas complet ni poussé.
			Par manque de temps et de ressources, nous n'avons pas pu
			explorer plus loin notre quête de résultats.

	Pour nos benchmark nous avons procédé par une évaluation basé sur plusieurs
	métrique définissant nos bases de données de variables booléennes: 
		- une croissance du nombre de variables (avec une taille de transaction proportionnelle statistiquement.)
		- une croissance du nombre de transaction.
		- une croissance de tout ces paramètres en même temps.
	
	Nous avons répéter ces tests dans les limites de nos ressources et de notre temps
	sur 3 fréquences minimal données: 0.1, 0.5 et 0.9.

	Comme attendu, on a pu observer une évolution nettement supérieur dans preque tous les cas 
	de Apriori par rapport à celle de FPGrowth, à l'exception des évaluation pour les tests 
	avec une fréquences à 0.9 avec lesquels ont observe une évolution similaire quoi qu'un peu
	plus élevé pour FPGrowth.

	Si on se base sur ces résultats, on pourrait dire qu'il ressemble à ceux que l'on attendait.
	Cependant, comme évoqué précedement, ces résultats ne sont pas assez poussé, le choix des métriques
	également, et notre implémentation de FPGrowth est elle aussi à revoir car non
	satisfaisante par endroit (même si elle reste apréciable pour illustrer l'idée derrière cette méthode.).