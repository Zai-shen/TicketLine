import { AfterViewInit, ChangeDetectorRef, Component, ViewChild } from '@angular/core';
import { ErrorType, NewsDTO, UserDTO } from '../../../generated';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { ErrorMessageComponent } from '../error-message/error-message.component';
import { NewsService } from '../../services/news.service';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'tl-news-detail',
  templateUrl: './news-detail.component.html',
  styleUrls: ['./news-detail.component.css']
})
export class NewsDetailComponent implements AfterViewInit {

  news: NewsDTO;
  imageSource: string = 'https://picsum.photos/800/200';

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  constructor(private newsService: NewsService, private authService: AuthService,
    private route: ActivatedRoute, private changeDetectorRef: ChangeDetectorRef,
    private userService: UserService) {
  }

  ngAfterViewInit(): void {
    this.route.params.subscribe(params => {
      const id: number = +params['id'];
      if (isNaN(id)) {
        this.errorMessageComponent.throwCustomError('Die bereitgestellte News ID ist nicht valide', ErrorType.FATAL);
        this.changeDetectorRef.detectChanges();
      } else {
        this.newsService.getNews(+params['id']).subscribe(
          (news: NewsDTO) => {
            this.news = news;
            this.updateImage();
            this.addReadNews();
          },
          error => {
            this.errorMessageComponent.defaultServiceErrorHandling(error);
            this.changeDetectorRef.detectChanges();
          }
        );
      }
    });
  }

  updateImage(): void {
    if (this.news.picturePath !== undefined && this.news.picturePath !== null) {
      if (this.news.id !== undefined && this.news.id !== null) {
        // this.imageSource = '/static/' + this.news.picturePath + '.png';
        this.newsService.getPictureOfNewsWithId(this.news.id).subscribe(
          (base64image: string) => {
              console.log(base64image);
          },
          error => {
            this.errorMessageComponent.defaultServiceErrorHandling(error);
            this.changeDetectorRef.detectChanges();
          }
        );
      }
    }
  }

  addReadNews(): void {
    this.userService.addReadNewsOfUser(this.news).subscribe(
      (_success: any) => {
        console.log('Daten Gespeichert für News mit ID' + this.news.id);
      },
      error => {
        this.errorMessageComponent.defaultServiceErrorHandling(error);
        this.changeDetectorRef.detectChanges();
      }
    );
  }

  formatDate(theDate: string): string {
    const theConverted = new Date(theDate);
    return theConverted.toDateString() + ' um ' + theConverted.getHours() + ':' + theConverted.getMinutes()
      + ':' + theConverted.getSeconds();
  }
}
