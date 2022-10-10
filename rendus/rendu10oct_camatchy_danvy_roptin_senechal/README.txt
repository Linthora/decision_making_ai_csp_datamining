Groupe :
	21801009 Alexander Camatchy
	21914304 Alix Danvy
	22005759 Mathis Roptin
	22003660 Félix Sénéchal 
	
Mode d'emploi:
	Note: 	pour compiler correctement, il est nécessaire d'avoir
		un répertoire lib contenant la librairie de tests fourni 
		(à côté du répertoire src)
		
	script ant:
		$ ant : pour compiler le projet.
		$ ant compile : pour compiler le projet.
		$ ant doc : pour générer la documentation du projet.
		$ ant test : pour lancer les tests sur tous 
				les éléments du projet.
		$ant testqueue :  pour lancer les tests concernant 
				uniquement la PriorityQueueHeap.

Note:
	Les seules classes executable nécessaire jusque là sont celles 
	lançant les tests : test.Test  et  test.TestPriorityQueueHeap.
	
	Nous avons réalisé en plus de ce qui était demandé le planner
	BeamSearch en créant une priority queue avec une capacité maximale
	pour le faire fonctionner.
	
	Toutes les informations nécessaires à la compréhenssion du codes
	sont disponible dans la documentation fourni. 
	(sous forme de commentaire dans le code directement 
	ou en générant la java-doc)
