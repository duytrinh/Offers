//
//  RecommendDetailsViewController.h
//  DemoOfferSDK
//
//  Created by TTA-Brown-005 on 2/5/15.
//  Copyright (c) 2015 TTA-Brown-005. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <OffersKit/OffersKit.h>

@interface RecommendDetailsViewController : UIViewController

@property(nonatomic,weak)   IBOutlet UITextView*       nameLabel;
@property(nonatomic,weak)   IBOutlet UIImageView*   imageView;
@property(nonatomic,strong)   OffersRecommendation *offers;

@end
