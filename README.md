﻿# Creature Store
Content Creator Web Application
asd,has
Content Creator წარმოადგენს ვებ- აპლიკაციას რომლის საშუალებითაც ამ პროფესიის მქონე ადამიანებს შეეძლებათ დარეგისტრირდნენ  და ატვირთონ, გააზიარონ თავიანთი ნამუშევარი, ნახონ სხვა მომხმარებლების ნამუშევრები follow-ს საშუალებით. 

მომხმარებლის აუდენტიფიკაცია
ვებ აპლიკაციაში შესასვლელად შესასვლელად საჭიროა სისტემაში ავტორიზაცია, ავტორიზაციისთვის პირველ ჯერზე მომხმარებლემა უნდა გაიარონ რეგისტრაცია რომლიც შესაძლებელი იქნება როგორც რეგისტრაციის ფორმის შევსებით ასევე Google  და Facebook account-ით.

აპლიკაციაში მომხმარებლის მიერ შესასრულებებლი დასაშვები ქმედებები განისაზღვრება მისი პრივილეგიით.

2. სისტემის მომხმარებლები
სისტემაში სულ იქნება 3 ტიპის მომხმარებელი: ადმინისტრატორი, მომხმარებელი და guest.

guest - ექნება საშუალება(რეგისტრაციის გარეშე) უბრალოდ დაათვალიეროს მთავარ გვერდზე არსებული პოსტები და სურვილის შემთხვევაში გადავიდეს რომელიმე მომხმარებლის გვერდზე და კონკრეტულად ერთი მომხმარებლის ატვირთული პოსტები დაათვალიეროს

მომხმარებელი -  guest-ის საშუალებებთან ერთად  დამატებით ექნება following ის საშუალება, ასევე სხვისი ნამუშევრების შეფასება + - ების საშუალებით. ექნება საკუთარი გვერდი სადაც შეეძლება ატვირთოს თავისი ნამუშევრები.

ადმინისტრატორი  - ადმინისტრატორის პრივილეგიის მქონე მომხმარებელი, სისტემის ჩვეულებრივი მომხმარებლისგან განსხვავებით, დამატებით განახორციელებენ შემდეგ ქმედებებს:
მომხმარებელზე ban -ის დადება და პირიქითაც
სხვა მომხმარებლის პოსტის წაშლა
სხვა მომხმარებლისთვის ადმინისტრატორის პრივილეგიის მინიჭება და პირიქითაც


3. პოსტი
ადმინისტრატორს და მომხმარებელს შესაძლებლობა ექნება სისტემაში დაამატოს ახალი პოსტი და ასევე განახორციელოს რედაქტირება მის მიერ დამატებული პოსტის.
პოსტი მოიცავს შემდეგ ველებს :
ავტორი
სურათი
ვიდეო(იუთუბის ლინკი)
სათაური
ტექსტი
tags list
დამატების თარიღი
‘+’ -ების რაოდენობა
‘-’ -ების რაოდენობა

მომხმარებელს შეეძლება გადავიდეს პოსტის გვერდზე, სადაც გამოსახული იქნება პოსტის ინფორმაცია. პოსტი შედგება ავტორისგან, სათაურისგან, tag-ებისგან, სურათისა და ტექსტისგან. დარეგისტრირებულ მომხმარებლებს შეეძლებათ შეფასება (+/-). პოსტის ავტორს შეეძლება რედაქტირება.

4. სისტემის რესურსების ძებნა და დათვალიერება
საიტზე შესვლის შემდგომ ყველა ტიპის მომხმარებელი გადავა ძირითად ძებნის გვერდზე (dashboard). ამ გვერდზე მომხმარებლებს შესაძლებლობა ექნებათ ნახონ ყველაზე პოპულარული, da-follow-ებული მომხმარებლების  და ბოლოს დამატებული პოსტები. 

თითოეული პოსტის ქვემოთ იქნება + - ღილაკი, ხოლო ზემოთ ავტორის სახელი, რომელზე დაკლიკების შემთხვევაშიც გადავალთ თითონ ამ მომხმარებლის გვერდზე. პოსტზე დაკლიკების შემთხვევაში გადავა უშუალოდ ამ პოსტის გვერდზე

დარეგისტრირებულ მომხმარებლებს შეეძლებათ ნახონ და-follow-ებული მომხმარებლების პოსტები. paging-ის საშუალებით შესაძლებელი იქნება ყველა პოსტის ნახვა. ასევე იქნება ძებნა ფილტრების საშუალებით. მაგალითად: მომხმარებლის, tag-ების ან პოსტის სათაურის მიხედვით. 



5. მომხმარებელი
მომხმარებელი მოიცავს შემდეგ ველებს:
სახელი(*)
გვარი(*)
password(*)
display name
სურათი
e-mail(*)
სხვა საკონტაქტო ინფორმაცია(fb, twitter, g+...)
ადგილმდებარეობა
About Me

*-ით მონიშნული გვერდები სავალდებულოა

სისტემაში დარეგისტრირებულ მომხმარებლებს შეეძლებათ შეიყვანონ თავიანთი ინფორმაცია. შეუძლიათ e-mail-ის გარდა ყველა ველის რედაქტირება. 

6. მომხმარებლის გვერდი
მომხმარებელზე დაკლიკების შემთხვევაში უნდა გაიხსნას მომხმარებლის გვერდი.
ამ გვერდზე გამოსახული იქნება მომხმარებლის მიერ მითითებული ინფორმაცია. ასევე:
მის მიერ ატვირთული პოსტები
followers
following
მომხმარებლის და+ებული პოსტები.
მომხმარებლის და-ებული პოსტები

7. ადმინისტრატორის სამართავი პანელი
ამ პანელზე გამოსახული იქნება 1 ღილაკი:
მომხმარებლები

მომხმარებლის ღილაკზე დაჭერისას ადმინისტრატორი გადავა მომხმარებლების გვერდზე რომელზეც იქნება ჩამოთვლილი ყველა მომხმარებელი. ფილტრების საშულებით შესაძლებელი იქნება მომხმარებლების გაფილტვრა და საჭიროების შემთხვევაში მათზე ბანის დადება ან ადმინისტრატორის სტატუსის მინიჭება შესაბამისი ღილაკის საშუალებით.
ფილტრები:
სახელი
გვარი
e-mail
display name

Additonal:

google analytics
comments
ნიუს ფიდი უნდა მუშაობდეს რეალთაიმში. როგორც კი ჩემი ფოლოუერი რამეს დადებს ეგრევე უნდა გამომიჩნდეს ფიდში.



