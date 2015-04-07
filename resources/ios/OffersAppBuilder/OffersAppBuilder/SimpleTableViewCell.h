//
//  SimpleTableViewCell.h
//  DemoOfferSDK
//
//  Created by TTA-Brown-005 on 2/3/15.
//  Copyright (c) 2015 TTA-Brown-005. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface SimpleTableViewCell : UITableViewCell

@property (strong, nonatomic) IBOutlet UILabel *couponTitle;
@property (strong, nonatomic) IBOutlet UITextView *couponContent;
@property (strong, nonatomic) IBOutlet UIImageView *couponImage;
@property (strong, nonatomic) NSString *couponType;
@property (strong, nonatomic) NSString *couponURL;
@property NSUInteger couponId;

@end
