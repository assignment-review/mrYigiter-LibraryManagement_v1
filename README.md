# LibraryManagement_v1

Library Management System

  ![1](https://user-images.githubusercontent.com/117197515/235967319-3240bec1-b132-493c-8ada-d29b33e0d53d.jpg)


Sprin Boot ile PostgreSQL e  JDBC ile bağlantı kurularak hazırlanmıştır

1- Yeni Spring Boot Project sekmesinden gerekli kutuphaneleri ekleyerek bir proje oluşturdum.
	        Maven Kütüphaler :
	            -data-jpa, -validation, -starter-web, -devtools, -postgresql, -lombok, -starter-jdbc, -webmvc-ui
              
2- Application.yml dosyasında port, database ayarlamalarımı yaptım
	            -DB name: library_management_v1_db
	            -username: db_user
                    -password: db_password		    

3- İstenilen Entity Classları de DTO classlarını oluşturdum.
   
  ![3](https://user-images.githubusercontent.com/117197515/235959726-6d502665-9a61-40c8-a9d9-eb5b457cb322.jpg)

-DTO class ile DB deki fieldları client tarafına geçirmemek
-Mapping işlemi ile kayıt işlemleri oldu (MapStruct) kutuphanesini eklemedim

4- Management Classında yapılması istenen methodları Controller package(BookController, BorrowerController) ile ayrı olarak yaptım.

  ![4](https://user-images.githubusercontent.com/117197515/235964554-27b93275-64b8-4459-ba01-faaf78d7315f.jpg)


5- Service katmanında (Controller) Client tarafından gelen (DTO) classları bu katmanda DB den sql Jdbc ile sorgular yaparak data alış-verişi yaptığım katman.

6- kullanıcıların browser üzerinden sisteme etkileşimde bulunabilmesi için Swagger kutuphanesini maven a ekleyerek bir arayuz oluşturdum.

8- Uygulamyı yazarken  Nesne Tabanlı bir yaklaşıma uygun yazmaya calıştım
	
Uygulamanın kısaca acıklaması
	
• addBook: Bu Method clientın gonderdiği BookDTO classındaki variableları DB kayıt işlemi yapar ve client a geri dondurur ama DB de id kısmı yada Pojo class return edilmez 

  ![5](https://user-images.githubusercontent.com/117197515/235964634-3ec8add7-aea2-4909-a037-89a9b2cccc08.jpg)

	
• removeBook: Bu method client silmek istediği book title bilgisini gonderir bizde DB de once var olup olmadığına bakarız varsa sileriz yok ise kitap bulunamadı retturn ettim


• getBook: Bu method isbn no ile DB de olan book return etmesi için DB de isbn varlığını kontrol ettim varsa datayı, yoksa kitap bulunamadı return ettim

  ![6](https://user-images.githubusercontent.com/117197515/235998831-ce6a2771-9e07-45d7-b210-1dacc3c6e3e3.jpg)

	
• borrowBook: Bu method kitap odunc verme işlemi yapar ancak bir sart var AvailableCopies 0 dan buyuk olmali ve kitap varlığını kontrol bu işlemlerine gore odunc verilebilir bu logic işemini kontrol ederek AvailableCopies variablesini 1 azaltarak book entitysini guncelleme yaptım (Burada Kim aldığı bilinmiyor)

  ![7](https://user-images.githubusercontent.com/117197515/235999349-48ec07dc-22e4-4ea1-b886-93dd266b4af4.jpg)

	
• returnBook: Bu method da Kitap varlığını kontrol ettkten sonra book entity class ını 1 artırdım *** Ama Bu taskte gelen kitap her defasında 1 artırılarak ilk oluşturulduğundan daha fazlasına gidiyor ilk AvailableCopies degerini sabit olarak tutan variable olamdığı için max seviyede durdurma yapılamadı Taske  gore ***
			
• addBorrower: Bu method yeni borrower eklemsi yapar ancak email i unique olsun dedim ve kayıtlı email kontorlu yaptıktan sonra borrower eklemesi yaptım
	
• removeBorrower: Kayıtlı email varsa bu method ile silme işlemi gercekleştirdim
	
• getBorrower: Kayıtlı email i varsa return eder yoksa borrower bulunamadı dondurdum
	
** Task i anladığım kadaryla verilenlere sadık kalarak yapmaya calıştım Ancak bazı geliştirilmesi gereken yerler olduğu için 
Library Management v2 olarak uygulamayı yeniden yazdım yapılması gerekenler şeklinde Bir task yok ama verilen taskten yola cıkarak bazı guncellemeler yaptım. Bunlar 
		
- Kitap ile Borrower arasında bir ilişki yok:Loan entity olşturarak 2 classı loan classi ile uni-direction OneToOne ilişki ile bağladım borrow ve return tarihleri kim hangi kitabı aldığı kayıt altına alınsın
- hangi kitabı hangi kullnıcı aldığı bilinmiyor: bende loan classında verilen kitapları kim ne zaman kaçtane aldığı kaydını tutmak için işlem yaptım
- kullanıcı  ne kadar kitap odunc alabiliyor bilinmiyor: Ben de loan clasda borrower in aldığı kitapların kaydı tutuldğundan getirmedikleri sayısını DB den Sql sorgu ile  bularak cektim ve 3 adeti gecemez sartı koydum.
- aynı kitaptan 1 den fazla alabiliyor: bu kontrolude loanda borrowerın alıp getirmediği kitap isimlerini liste olarak db den sql sorgu ile aldım sonra almak istediği bu listede varsa bu kitap sende var şeklinde mesaj return ettim
- Return edilen kitap kutuphadeki ilk oluşturulduğu sayıyı geciyor: bunuın kontrolunude loanda alınan kitapların gelip gelmediği kontrolunu status isminde bir boolean variable oluşturdum borrower kitabı aldığı zaman status=false return ettiği zaman status=true oluyor ve aynı borrower aynı kitabı return etmek isterse status u kontrol ederek geri iade işlemi yapmasını engellemiş oldum boylece tum borrowerlar kitapları return ettğinde AvailableCopies ilk sayısına ulasır ve fazladan kitap eklenmez
- toplu olarak kitap listesi ve borrower listesi bulunmuyor: 
3 entity classlar için getAll methodları yaparak liste halinde gosterdım
		
*** bu Kendimi ve uygulamayı geliştirme adına yaptıklarımın yanında bir de Security katmanını ekledim role tanımlaması yaparak member ve admın şeklinde	her işlemi admin yapabilir ancak member sadece kitap alma ve return işlemlerini yapsın şeklinde guvenlik katmanı ekledim 
				
-- eksik kalan ve yapılması gereken işlemlerimde bulunuyor orneğin Kullancı bilgileri nı kitap bilgilerini yada password yenileme (yani update işlemleri) kısımları var	task den yola cıkarak yazmaya calışsacağım
		






