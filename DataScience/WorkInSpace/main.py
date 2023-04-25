import pygame, sys, time
import pygame.time

from settings import *
from sprites import BG, Ground, Rocket, Obstacle
from pygame import mixer

class Game:
    def __init__(self):
        #setup
        pygame.init()
        self.display_surface = pygame.display.set_mode((WINDOW_WIDTH, WINDOW_HEIGHT))
        pygame.display.set_caption("Trabalho no Espaço by Christian")
        self.clock = pygame.time.Clock()
        self.active = True

        # sprite groups
        self.all_sprites = pygame.sprite.Group()
        self.collision_sprites = pygame.sprite.Group()

        # scale factor
        bg_height = pygame.image.load('graphics/bg.jpg').get_height()
        self.scale_factor = WINDOW_HEIGHT / bg_height

        # sprite setup
        BG(self.all_sprites, self.scale_factor)
        Ground([self.all_sprites, self.collision_sprites], self.scale_factor)
        self.rocket = Rocket(self.all_sprites, self.scale_factor / 1.7)

        #timer
        self.obstacle_timer = pygame.USEREVENT + 1
        pygame.time.set_timer(self.obstacle_timer, 1400)

        #text
        self.font = pygame.font.Font('graphics/fonts/NiseSegaSonic.ttf', 30)
        self.score = 0
        self.start_offset = 0

        # menu
        self.menu_surf = pygame.image.load('graphics/menu.png').convert_alpha()
        self.menu_rect = self.menu_surf.get_rect(center = (WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2))

        #mixer.init()
        #mixer.music.load('sounds/roving_mars.mp3')
        #mixer.music.play()
        #music
        self.music = pygame.mixer.Sound('sounds/roving_mars.mp3')
        self.music.play(loops = -1)

        self.gameover_sound = pygame.mixer.Sound('sounds/game_over.mp3')

    def display_score(self):
        if self.active:
            self.score = (pygame.time.get_ticks() - self.start_offset) // 1000
            y = WINDOW_HEIGHT / 10
        else:
            y = WINDOW_HEIGHT / 2 + (self.menu_rect.height / 1.5)

        score_surf = self.font.render('Score: ' + str(self.score),True,'black')
        score_rect = score_surf.get_rect(midtop = (WINDOW_WIDTH / 2, WINDOW_HEIGHT / 10))
        self.display_surface.blit(score_surf, score_rect)
    def collisions(self):
        if (pygame.sprite.spritecollide(self.rocket, self.collision_sprites, False, pygame.sprite.collide_mask) \
        or self.rocket.rect.top <= 0):
           for sprite in self.collision_sprites.sprites():
               if sprite.sprite_type == 'obstacle':
                    sprite.kill()
           self.active = False
           self.gameover_sound.play()
           self.rocket.kill()
    def run(self):
        last_time = time.time()
        while True:
            # delta time
            dt = time.time() - last_time
            last_time = time.time()

            pygame.joystick.init()
            joysticks = [pygame.joystick.Joystick(x) for x in range(pygame.joystick.get_count())]

            # event loop
            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    pygame.quit()
                    sys.exit()
                if event.type == pygame.MOUSEBUTTONDOWN or event.type == pygame.JOYBUTTONUP:
                    if self.active:
                        self.rocket.jump()
                    else:
                        self.rocket = Rocket(self.all_sprites, self.scale_factor / 1.7)
                        self.active = True
                        self.start_offset = pygame.time.get_ticks()
                if event.type == self.obstacle_timer and self.active:
                    Obstacle([self.all_sprites, self.collision_sprites], self.scale_factor * 1.2)

            # game logic
            self.display_surface.fill('black')
            self.all_sprites.update(dt)
            #self.collisions()
            self.all_sprites.draw(self.display_surface)
            self.display_score()

            if self.active:
                self.collisions()
            else:
                self.display_surface.blit(self.menu_surf, self.menu_rect)
                gameover_surf = self.font.render("Game Over", True, 'red')
                gameover_rect = gameover_surf.get_rect(midtop=(WINDOW_WIDTH / 2, (WINDOW_HEIGHT / 50) + 100))
                self.display_surface.blit(gameover_surf, gameover_rect)

            pygame.display.update()
            self.clock.tick(FRAMERATE)

if __name__ == '__main__':
    game = Game()
    game.run()