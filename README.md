# Minesweeper
- 강의명 : 2019학년도 2학기 소스코드 분석
- 과제 내용 : 기존 Minesweeper 게임에서의 기능 추가
- 팀명 : 3조
  - [이주형][member1] 
  - [서예진][member2]
</br>

## 기능 추가 내용 
### 1. 'Battle'이라는 2인용 Mode 추가
- 새로 제작한 Battle Mode에서는 2명의 Player가 키보드를 이용해 게임을 하게 된다. Player1은 키보드에서 AWDS로 방향을 조작하고 F로 지뢰를 탐색한다. Player2는 키보드에서 방향키로 방향을 조작하고 Enter로 지뢰를 탐색한다. Player들은 서로 턴을 주고 받으며 누가 더 빨리 모든 지뢰를 찾는지 대결하게 된다.

![image](https://user-images.githubusercontent.com/50551349/121234719-00139180-c8cf-11eb-809c-0d4542a9f5b8.png)

![image](https://user-images.githubusercontent.com/50551349/121234810-16b9e880-c8cf-11eb-8962-5685e51ea324.png)

![image](https://user-images.githubusercontent.com/50551349/121234834-1cafc980-c8cf-11eb-9f07-da9cec8e8207.png)

</br>

### 2. Easy, Medium, Hard에서의 Ranking Board를 통한 1~20위 기록 누적 및 표시
- 기존 게임에서는 1위의 기록만 표시되도록 설정되어 있었다. 기록의 누적의 범위를 1위가 아닌 20위까지 저장하도록 설정하였으며, 이를 별도의 윈도우 창에서 확인할 수 있도록 하였다. Battle에서의 랭킹은 무의미할 것 같아 이는 생략했다. 

![image](https://user-images.githubusercontent.com/50551349/121235024-5c76b100-c8cf-11eb-8e68-516a92808987.png)

</br>

### 3. 'Battle'에서의 Player들의 목숨 추가
- Battle Mode에서 Player들의 목숨을 추가하였다. 기본으로 15개의 목숨이 제공되며 Player가 지뢰를 못 찾을 경우 목숨이 1개씩 감소한다. 반대로, 지뢰를 찾게 되면 목숨의 개수가 1개씩 증가된다. Player의 목숨이 0이 되면 게임은 종료된다.

![image](https://user-images.githubusercontent.com/50551349/121234810-16b9e880-c8cf-11eb-8962-5685e51ea324.png)

![image](https://user-images.githubusercontent.com/50551349/121234834-1cafc980-c8cf-11eb-9f07-da9cec8e8207.png)
 
</br>

### 4. 'Battle'에서의 포션(Potion) 추가
- Battle Mode에서 포션을 추가하였다. 포션은 지뢰와 같이 랜덤으로 배치하였으며, Player가 포션을 찾으면 목숨이 1개씩 증가한다. 포션은 Player마다 각각 3개씩 배치하였다.

<img src="https://user-images.githubusercontent.com/50551349/121237654-428a9d80-c8d2-11eb-914c-5257d8c948f7.png" width="200%"/>

</br>

### 5. 기존 Mode(Easy, Mdeium, Hard)에서의 상태바(경과시간 & 남은 마크 찬스) 추가
- 기존 Mode(Easy, Medium, Hard)에서 상태바를 추가하였다. 상태바에는 경과시간과 남은 마크 찬스를 표시하였다. 남은 마크 찬스는 마우스를 우클릭하였을 때 나타나는 ‘빨간색 배경의 !표시’이다. 남은 지뢰의 개수를 나타내게 되면 마크를 할 때마다, 그 위치가 지뢰의 유무가 맞는지 식별이 되어야한다. 이는 게임의 취지에서 벗어나고 난이도를 급감시키는 기능이므로 ‘남은 지뢰 개수’ 대신에 ‘남은 마크 찬스’만 나타내게 된다.
  
<img src="https://user-images.githubusercontent.com/50551349/121237856-749bff80-c8d2-11eb-9806-9a0a205b2bf4.png" width="200%"/>
 
</br>

### 6. 배경음 및 효과음 추가
- 게임이 시작될 때 배경음이 재생되고, 기존 Mode에서 지뢰가 터질 때와 Battle모드에서 플레이어의 목숨이 0이 될 때 효과음이 재생된다. 
   
</br>

### 7. 실패 시 모든 지뢰 표시
- 기존 Mode(Easy, Medium, Hard)에서 지뢰가 터져서 게임에 실패할 경우, 남은 지뢰가 어디에 있었는지 모두 표시된다. 

![image](https://user-images.githubusercontent.com/50551349/121237916-85e50c00-c8d2-11eb-8e23-3021c2c3acfc.png)



</br>
 
## Class Diagram
![2차과제모델(개선후ClassDiagramPackage)_3조_이주형_서예진](https://user-images.githubusercontent.com/50551349/121234572-cd699900-c8ce-11eb-852a-a22f9b08a269.png)

</br>

## 사용 기술 스택
- Java



[member1]:https://github.com/yamiblack
[member2]:https://github.com/yejin25
