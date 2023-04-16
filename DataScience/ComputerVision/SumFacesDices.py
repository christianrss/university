import cv2
import numpy as np

imagemDados = cv2.imread('imagensDados/img4.jpg')

# Converte a imagem para preto e branco
imgGray = cv2.cvtColor(imagemDados, cv2.COLOR_BGR2GRAY)

img = imgGray

# Remove ruidos da imagem
img = cv2.medianBlur(img, 7)

# Segmenta a area de interesse (bolinhas dos dados) 
rr, img = cv2.threshold(img, 230, 255, cv2.THRESH_BINARY_INV)

# Dilata as bordas da area de interesse (bolinhas dos dados)
elemento = cv2.getStructuringElement(cv2.MORPH_RECT, (3,3))
img = cv2.dilate(img, elemento, iterations=1)

# Remove ruidos restantes
img = cv2.medianBlur(img, 7)

# Inverte as cores da area de interesse e fundo
img = 255 - img

rows = img.shape[0]

# Detecta os circulos
circulos = cv2.HoughCircles(img, cv2.HOUGH_GRADIENT, 1, 15, param1=100, param2=9, minRadius=4, maxRadius=15)
circles = np.uint16(np.around(circulos))

soma_faces = 0

# Desenha os circulos
for i in circles[0,:]:
    center = (i[0], i[1])
    radius = i[2]
    cv2.circle(imagemDados, center, radius, (0, 0, 255), 2)
    soma_faces +=1

str_resultado = 'Soma das faces: ' + str(soma_faces);
str_resultado_posicao = (10,50)
str_resultado_fonte_tamanho = 1.5
str_resultado_fonte = cv2.FONT_HERSHEY_COMPLEX
str_resultado_fonte_cor = (0, 0, 255)
str_resultado_fonte_grossura = 4

cv2.putText(
    imagemDados,
    str_resultado,
    str_resultado_posicao,
    str_resultado_fonte,
    str_resultado_fonte_tamanho,
    str_resultado_fonte_cor,
    str_resultado_fonte_grossura
)

# Redimensiona a imagem mantendo a escala, para uma melhor visualizacao.
img_escala = 60 
img_width = int(imagemDados.shape[1] * img_escala / 100)
img_height = int(imagemDados.shape[0] * img_escala / 100)
dimensoes = (img_width, img_height) 
resizedImg = cv2.resize(
    imagemDados,
    dimensoes,
    interpolation = cv2.INTER_AREA
)

cv2.imshow('Resultado', resizedImg)

cv2.waitKey(0)
cv2.destroyAllWindows()