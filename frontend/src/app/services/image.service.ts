import {NewsApiService} from '../../generated';

export class ImageService extends NewsApiService {

  getImageUrl(imageId: string) {
    return imageId;
  }

}
